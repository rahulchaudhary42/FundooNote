package com.bridgelabz.fundoo.notes.service;

import java.io.IOException;
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

import com.bridgelabz.fundoo.elasticsearch.IElasticsearch;
import com.bridgelabz.fundoo.exception.NotesException;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.INotesRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@Autowired
	private IElasticsearch elasticsearch;

	String INDEX = "es";
	String TYPE = "createnote";

	@SuppressWarnings("unused")
	@Autowired
	private ObjectMapper objectMapper;

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
		Note savednote = notesRepository.save(note);

		try {
			elasticsearch.createNote(savednote);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Note> notes = user.get().getNotes();

		if (notes != null) {
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
		Note upadtednote =notesRepository.save(notes);
		try {
			elasticsearch.updateNote(upadtednote);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			Note note=notesRepository.save(notes);
			try {
				elasticsearch.deleteNote(note.getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashError"),
				Integer.parseInt(environment.getProperty("status.note.errorCode")));
		return response;
	}
	
	
//	@Override
//	public Response delete(String token, String noteId) {
//		String id = userToken.tokenVerify(token);
//		Note notes = notesRepository.findByIdAndUserId(noteId, id);
//		if(notes == null) {
//			throw new NotesException("Invalid input", -5);
//		}
//		if(notes.isTrash() == false) {
//			notes.setTrash(true);
//			notes.setModified(LocalDateTime.now());
//			notesRepository.save(notes);
//			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),Integer.parseInt(environment.getProperty("status.success.code")));
//			return response;
//		}
//		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashError"),Integer.parseInt(environment.getProperty("status.note.errorCode")));
//		return response;
//	}

	@Override
	public Response setColor(String token, String colorCode, String noteId) {
		String uderId = userToken.tokenVerify(token);
		Note note = notesRepository.findByIdAndUserId(noteId, uderId);
		note.setColorCode(colorCode);
		 
		note.setModified(LocalDateTime.now());
		Note ab = notesRepository.save(note);
		try {
			elasticsearch.createNote(ab);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("color->" + ab);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.color"),
				Integer.parseInt(environment.getProperty("status.success.code")));
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
		if (notes == null) {
			throw new NotesException("Invalid input", -5);
		}
		if (notes.isArchive() == false) {
			notes.setArchive(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.archieved"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			notes.setArchive(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.unarchieved"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	public List<Note> getAllNotes(String token) {
		String id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);

//		for (Note userNotes : notes) {
//			NotesDto notesDto = modelMapper.map(userNotes, NotesDto.class);
//			if (userNotes.isArchive() == false && userNotes.isTrash() == false) {
//				listNotes.add(notesDto);
//			}
//		}
		return notes;
	}

	@Override
	public Response trashAndUnTrash(String token, String id) {
		String ide = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(id, ide);
		if (notes.isTrash() == false) {
			notes.setTrash(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			notes.setTrash(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.untrashed"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	@Override
	public List<Note> getArchiveNotes(String token) {
		String id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for (Note userNotes : notes) {
			Note notesDto = modelMapper.map(userNotes, Note.class);
			if (userNotes.isArchive() == true && userNotes.isTrash() == false) {
				listNotes.add(notesDto);
			}
		}
		return listNotes;
	}

	@Override
	public List<Note> getTrashNotes(String token) {
		String id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for (Note userNotes : notes) {
			Note notesDto = modelMapper.map(userNotes, Note.class);
			if (userNotes.isTrash() == true) {
				listNotes.add(notesDto);
			}
		}
		return listNotes;
	}

	@Override
	public Response deletePermanently(String token, String id) {
		String ide = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(id, ide);
		if (notes.isTrash() == true) {
			notesRepository.delete(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.deleted"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.notdeleted"),
					Integer.parseInt(environment.getProperty("status.note.errorCode")));
			return response;
		}
	}

//	@Override
//	public List<NotesDto> getPinnedNotes(String token) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	// @Override
//	public List<NotesDto> getPinnedNotes(String token) {
//		String id = userToken.tokenVerify(token);
//		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
//		List<NotesDto> listNotes = new ArrayList<>();
//		for(Note userNotes : notes) {
//			if(userNotes.isPin() == true && userNotes.isArchive() == false && userNotes.isTrash() == false) {
//			listNotes.add(notesDto);
//			}
//		}
//		return listNotes;
//	}

	@Override
	public List<Note> getUnPinnedNotes(String token) {
		String id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for (Note userNotes : notes) {
			if (userNotes.isPin() == false && userNotes.isArchive() == false && userNotes.isTrash() == false) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}

	@Override
	public List<Note> getPinnedNotes(String token) {
		String id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for (Note userNotes : notes) {
			System.out.println("note pin-->" + userNotes.isPin());
			if (userNotes.isPin() == true && userNotes.isArchive() == false && userNotes.isTrash() == false) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}
   
	@Override
	public Response addReminder(String token, String noteId, String time) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findByUserId(userId);
		if(!user.isPresent())
			throw new NotesException("No user exist", -5);	
		Optional<Note> note = notesRepository.findById(noteId);
		note.get().setReminder(time);
		notesRepository.save(note.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.reminder.added"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}
	
	@Override
	public String getRemainders(String token, String noteId) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new NotesException("No user exist", -5);	
		Optional<Note> note = notesRepository.findById(noteId);
		String remainder = note.get().getReminder();
		return remainder;
	}
	
}
