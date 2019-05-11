package com.bridgelabz.fundoo.notes.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.NotesException;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.INotesRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

@Service("notesService")
@PropertySource("classpath:message.properties")
public class NotesServiceImpl implements INotesService{
	
	Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);
	
	@Autowired
	private UserToken userToken;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private INotesRepository notesRepository;
	
	@Autowired
	private Environment environment;

	@Override
	public Response createNote(NotesDto notesDto, String token) {
		System.out.println(notesDto.getTitle()+"\t"+notesDto.getDescription());
		String id = userToken.tokenVerify(token);
		logger.info(notesDto.toString());
		if(notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			
			throw new NotesException("Title and description are empty", -5);

		}
		Note note = modelMapper.map(notesDto, Note.class);
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent())
			System.out.print("No user Found");
			// throw exception
		note.setUserId(id);
		note.setCreated(LocalDateTime.now());
		note.setModified(LocalDateTime.now());
		note = notesRepository.save(note);
		
		List<Note> notes = user.get().getNotes();
		
		if(!(notes == null)) {
			notes.add(note);
			user.get().setNotes(notes);
		}else {
			notes= new ArrayList<Note>();
			notes.add(note);
			user.get().setNotes(notes);
		}
		
		
		
		userRepository.save(user.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.createdSuccessfull"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}
	
	

}
