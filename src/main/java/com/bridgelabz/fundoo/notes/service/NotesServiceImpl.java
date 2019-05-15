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
public class NotesServiceImpl implements INotesService {

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
		System.out.println(notesDto.getTitle() + "\t" + notesDto.getDescription());
		String id = userToken.tokenVerify(token);
		logger.info(notesDto.toString());
		
		if (notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			throw new NotesException("Title and description are empty", -5);
		}
		
		Note note = modelMapper.map(notesDto, Note.class);
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			System.out.print("No user Found");
		// throw exception
		note.setUserId(id);
		note.setCreated(LocalDateTime.now());
		note.setModified(LocalDateTime.now());
		note = notesRepository.save(note);

		List<Note> notes = user.get().getNotes();

		if (!(notes == null)) {
			notes.add(note);
			user.get().setNotes(notes);
		} else {
			notes = new ArrayList<Note>();
			notes.add(note);
			user.get().setNotes(notes);
		}

		userRepository.save(user.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.createdSuccessfull"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response updateNote(NotesDto notesDto, String token, String id) {
		if (notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			throw new NotesException("Title and description are empty", -5);
		}

		String ide = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(id, ide);
		notes.setTitle(notesDto.getTitle());
		notes.setDescription(notesDto.getDescription());
		notes.setModified(LocalDateTime.now());
		notesRepository.save(notes);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.updated"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response delete(String token, String id) {
		String ide = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(id, ide);
		if (notes == null) {
			throw new NotesException("Invalid input", -5);
		}
		if (notes.isTrash() == false) {
			notes.setTrash(true);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashError"),
				Integer.parseInt(environment.getProperty("status.note.errorCode")));
		return response;
	}

	@Override
	public Response pinAndUnPin(String token, String id) {
		String ide = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(id, ide);
		if (notes == null) {
			throw new NotesException("Invalid input", -5);
		}
		if (notes.isPin() == false) {
			notes.setPin(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.pinned"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			notes.setPin(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.unpinned"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}
	
	@Override
	public Response archiveAndUnArchive(String token, String id) {
		String ide = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(id, ide);
		if(notes == null) {
			throw new NotesException("Invalid input", -5);
		}
		if(notes.isArchive() == false) {
			notes.setArchive(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.archieved"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setArchive(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.unarchieved"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	@Override
	public List<NotesDto> getAllNotes(String token) {
		String id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<NotesDto> listNotes = new ArrayList<>();
		for (Note userNotes : notes) {
			NotesDto notesDto = modelMapper.map(userNotes, NotesDto.class);
			if (userNotes.isArchive() == false && userNotes.isTrash() == false) {
				listNotes.add(notesDto);
			}
		}
		return listNotes;
	}

	@Override
	public Response trashAndUnTrash(String token, String id) {
		String ide = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(id, ide);
		if(notes.isTrash() == false) {
			notes.setTrash(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setTrash(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.untrashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}



}
