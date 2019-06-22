package com.bridgelabz.fundoo.user.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
 
import com.bridgelabz.fundoo.exception.LoginException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;
@Service
public class AmazonService {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private UserToken userToken;

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@SuppressWarnings("deprecation")
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);

	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public Response uploadFile(MultipartFile multipartFile, String token) throws IOException {
		String id = userToken.tokenVerify(token);
		// Optional<User> optionaluser = userRespository.findByUserId(id);
		Optional<User> optionaluser = userRepository.findById(id);
		if (optionaluser.isPresent()) {
			User user = optionaluser.get();
			String fileUrl = "";
			File file = convertMultiPartToFile(multipartFile);
			String fileName = generateFileName(multipartFile);
			fileUrl = "https://"+bucketName + ".s3.ap-south-1.amazonaws.com/" + fileName;
			// fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
			uploadFileTos3bucket(fileName, file);
			file.delete();
			user.setImage(fileUrl);
			userRepository.save(user);
			Response response = StatusHelper.statusInfo(environment.getProperty("aws.propic.successfull"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}

		Response response = StatusHelper.statusInfo(environment.getProperty("aws.propic.failuer"),
				Integer.parseInt(environment.getProperty("status.login.errorCode")));
		return response;
	}

	public Response deleteFileFromS3Bucket(String fileName, String token) {

		String id = userToken.tokenVerify(token);
		Optional<User> optionaluser = userRepository.findById(id);
		if (optionaluser.isPresent()) {
			User user = optionaluser.get();
			s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
			user.setImage(null);
			userRepository.save(user);
			Response response = StatusHelper.statusInfo(environment.getProperty("aws.propic.delete.successfull"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}

		Response response = StatusHelper.statusInfo(environment.getProperty("aws.propic.failuer"),
				Integer.parseInt(environment.getProperty("status.login.errorCode")));
		return response;
	}
	
	public URL getProfile(String token)
	{
		 
		String id =userToken.tokenVerify(token);
		boolean isUser = userRepository.findById(id).isPresent();
		if (!isUser) {
 
			System.out.println("Not found");
		}
		
		User user=userRepository.findById(id).get();
 
		String s1=user.getImage();
		URL url = null;
		try {
			url = new URL(s1);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	
	
	
	
	
	
	
	
}
