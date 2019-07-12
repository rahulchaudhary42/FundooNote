package com.bridgelabz.fundoo.notes.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.exception.NotesException;
//import com.bridgelabz.fundoo.exception.NotesException;
//import com.bridgelabz.fundoo.exception.TokenException;
import com.bridgelabz.fundoo.notes.dto.LabelDto;
//import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Label;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.INotesRepository;
import com.bridgelabz.fundoo.notes.repository.LabelRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

@Service("labelService")
@PropertySource("classpath:message.properties")
public class LabelServiceImpl implements ILabelService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private INotesRepository notesRepository;

	@Autowired
	private UserToken userToken;

	@Autowired
	private Environment environment;

	@Override
	public Response createLabel(LabelDto labelDto, String token) {
		System.out.println(labelDto.getLabelName());
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		if (labelDto.getLabelName().isEmpty()) {
			throw new LabelException("Label has no name", -6);
		}
		Optional<Label> labelAvailability = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if (labelAvailability.isPresent()) {
			throw new LabelException("Label already exist", -6);
		}

		Label label = modelMapper.map(labelDto, Label.class);

		label.setLabelName(labelDto.getLabelName());
		label.setUserId(userId);
		label.setCreatedDate(LocalDateTime.now());
		label.setModifiedDate(LocalDateTime.now());
		label = labelRepository.save(label);
		// user.get().getLabel().add(label);
		List<Label> labels = user.get().getLabel();

		if (!(labels == null)) {
			labels.add(label);
			user.get().setLabel(labels);
		} else {
			labels = new ArrayList<Label>();
			labels.add(label);
			user.get().setLabel(labels);
		}
		userRepository.save(user.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.created"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response deleteLabel(String labelId, String token) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if (label == null) {
			throw new LabelException("Invalid input", -6);
		}
		labelRepository.delete(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.deleted"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;

	}

	@Override
	public Response updateLabel(String labelId, String token, LabelDto labelDto) {
		//System.out.println(labelDto.getLabelName());
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if (label == null) {
			throw new LabelException("No label exist", -6);
		}
		if (labelDto.getLabelName().isEmpty()) {
			throw new LabelException("Label has no name", -6);
		}
		Optional<Label> labelAvailability = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if (labelAvailability.isPresent()) {
			throw new LabelException("Label already exist", -6);
		}
		label.setLabelName(labelDto.getLabelName());
		label.setModifiedDate(LocalDateTime.now());
		labelRepository.save(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.updated"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public List<Label> getAllLabel(String token) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}

		List<Label> labels = labelRepository.findByUserId(userId);
		List<Label> listLabel = new ArrayList<>();
		for (Label noteLabel : labels) {
			//LabelDto labelDto = modelMapper.map(noteLabel, LabelDto.class);
			listLabel.add(noteLabel);
		}
		return listLabel;
	}
 
 

//	
//	@SuppressWarnings("unused")
//	@Override
//	public Response addLabelToNote(String labelId, String token, String noteId) {
//		String id = userToken.tokenVerify(token);
//		Optional<User> optionalUser =userRepository.findById(id);
// 	    Optional<Note> optionalNote = notesRepository.findById(noteId);
// 		Optional<Label> optionalLabel = labelRepository.findById(labelId);
// 		if (optionalUser.isPresent() && optionalLabel.isPresent() && optionalNote.isPresent()) {
//			Label label = optionalLabel.get();
//			Note note = optionalNote.get();
//			System.err.println(label);
//			//note.setUpdateTime(Utility.todayDate());
//			List<Label> labels =  note.getListLabel();
//			if (labels != null) {
//				Optional<Label> opLabel = labels.stream().filter(l -> l.getLabelName().equals(label.getLabelName()))
//						.findFirst();
//				System.out.println(opLabel);
//				if (!opLabel.isPresent()) {
//					labels.add(label);
//					 
//					note.setListLabel(labels);
//					note = notesRepository.save(note);
//					System.out.println("save label in note" + note);
//					Response response =StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"),
//							Integer.parseInt(environment.getProperty("status.success.code")));
//					return response;
//				}
//			} 
//			else if (labels== null) {
//				labels = new ArrayList<Label>();
//				labels.add(label);
//				note.setListLabel(labels);
//				notesRepository.save(note);
//				Response response =StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"),
//						Integer.parseInt(environment.getProperty("status.success.code")));
//				return response;
//				}
//			
//			else {
//				Response response =StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"),
//						Integer.parseInt(environment.getProperty("status.success.code")));
//				return response;
//			}
//		
//	}
//		 
//		 
//
//		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"),
//				Integer.parseInt(environment.getProperty("status.success.code")));
//		return respons
//	}

	
	@Override
	public Response addLabelToNote(String labelId, String token, String noteId) {
		String userId = userToken.tokenVerify(token);
		Optional<User> optionalUser =userRepository.findById(userId);
		Optional<Note> note = notesRepository.findById(noteId);
		Optional<Label> label = labelRepository.findById(labelId);
		
		if(note.isPresent()) {
			List<Label> labelList = note.get().getListLabel();
			if(labelList!=null && !labelList.contains(label.get())) {
				labelList.add(label.get());
				note.get().setListLabel(labelList);
				notesRepository.save(note.get());
				System.out.println("Label == :"+label.get());
				System.out.println("if"+note.get());
			}else {
				List<Label> newLabelList = new ArrayList<Label>();
				newLabelList.add(label.get());
				note.get().setListLabel(newLabelList);
				notesRepository.save(note.get());
				System.out.println("Label == :"+label.get());
				System.out.println("LabelList == :"+newLabelList);
				System.out.println("else"+note.get());
			}
			Response response = StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"),
				Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
			
		}else {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.label.note.added"),
					Integer.parseInt(environment.getProperty("status.error.code")));
				return response;
		}
	}
	
	
	@Override
	public Response removeLabelFromNote(String labelId, String token, String noteId) {
		String userId = userToken.tokenVerify(token);
		Optional<User> optionaluser = userRepository.findById(userId);
		Optional<Note> optionalnote = notesRepository.findById(noteId);
		Optional<Label> optionallabel = labelRepository.findById(labelId);
		
				if(optionaluser.isPresent()&&optionalnote.isPresent()&&optionallabel.isPresent()) {
					Note note = optionalnote.get();
					Label label= optionallabel.get();
					List<Label> labels= note.getListLabel();
					if(labels.stream().filter(l->l.getLabelId().equals(label.getLabelId())).findFirst().isPresent()) {
						Label opsLabel= labels.stream().filter(list->list.getLabelId().equals(label.getLabelId())).findFirst().get();
						labels.remove(opsLabel);
						notesRepository.save(note);	
					}
						
				}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.removedfromnote"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}
	
	@Override
	public Response addNoteToLabel(String labelId, String token, String noteId) {
		String userId = userToken.tokenVerify(token);
		System.out.println("------------------");
		Optional<User> user = userRepository.findById(userId);
		System.out.println(user.isPresent());
		if (!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		System.out.println(label);
		if (label == null) {
			throw new LabelException("No such lebel exist", -6);
		}
		Note note = notesRepository.findByIdAndUserId(noteId, userId);

		System.out.println("note" + note);
		if (note == null) {
			throw new LabelException("No such note exist", -6);
		}
		note.getListLabel().add(label);
		notesRepository.save(note);
		label.setModifiedDate(LocalDateTime.now());
		//label.setNote(note);
		labelRepository.save(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response removeNoteFromLabel(String labelId, String token, String noteId) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if (label == null) {
			throw new LabelException("No such lebel exist", -6);
		}
		Note note = notesRepository.findByIdAndUserId(noteId, userId);
		if (note == null) {
			throw new LabelException("No such note exist", -6);
		}
		note.getListLabel().remove(label);
		notesRepository.save(note);
		label.setModifiedDate(LocalDateTime.now());
		//label.setNote(note);
		labelRepository.delete(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.removedfromnote"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

 
 
	
	@Override
	public List<Label>getLebelsOfNote(String token, String noteId) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("User does not exist", -6);
		}
		Optional<Note> note = notesRepository.findById(noteId);
		if(!note.isPresent()) {
			throw new NotesException("Note does not exist", -6);
		}
		List<Label>labels = note.get().getListLabel();
		 
		System.err.println("------");
		System.err.println(note.get().getListLabel());
 
	   return labels;
		
	}



}
