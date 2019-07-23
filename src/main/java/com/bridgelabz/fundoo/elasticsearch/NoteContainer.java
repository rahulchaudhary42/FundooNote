package com.bridgelabz.fundoo.elasticsearch;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.notes.model.Note;

@Component
public class NoteContainer {

	private Note notes;
	private NoteOperation noteoperation;

	public Note getNotes() {
		return notes;
	}

	public void setNotes(Note notes) {
		this.notes = notes;
	}

	public NoteOperation getNoteoperation() {
		return noteoperation;
	}

	public void setNoteoperation(NoteOperation noteoperation) {
		this.noteoperation = noteoperation;
	}

}
