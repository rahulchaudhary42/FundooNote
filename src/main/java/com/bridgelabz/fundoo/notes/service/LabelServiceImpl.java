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
import com.bridgelabz.fundoo.exception.TokenException;
import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
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
	public List<LabelDto> getAllLabel(String token) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}

		List<Label> labels = labelRepository.findByUserId(userId);
		List<LabelDto> listLabel = new ArrayList<>();
		for (Label noteLabel : labels) {
			LabelDto labelDto = modelMapper.map(noteLabel, LabelDto.class);
			listLabel.add(labelDto);
		}
		return listLabel;
	}

	@Override
	public Response addLabelToNote(String labelId, String token, String noteId) {
		String userId = userToken.tokenVerify(token);
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

		note.setModified(LocalDateTime.now());
		note.setLabel(label);
		// label.getNotes().add(note);
		notesRepository.save(note);
		System.out.println("label adeded");

		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response removeLabelFromNote(String labelId, String token, String noteId) {
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
		note.setModified(LocalDateTime.now());
		note.setLabel(label);
		note.getListLabel().remove(label);
		// label.getNotes().add(note);
		notesRepository.save(note);
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

		label.setModifiedDate(LocalDateTime.now());
		label.setNote(note);
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
		label.setModifiedDate(LocalDateTime.now());
		label.setNote(note);
		labelRepository.delete(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.removedfromnote"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}




}
