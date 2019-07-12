package com.bridgelabz.fundoo.notes.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "note")
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String userId;
	private String title;
	private String description;
	private String colorCode;
	private String reminder;
	private boolean isPin;
	private boolean isArchive;
	private boolean isTrash;
	private LocalDateTime created;
	private LocalDateTime modified;

	@DBRef
	private List<Label> listLabel;
	
	

	//private Label label;
//
//	public Label getLabel() {
//		return label;
//	}
//
//	public void setLabel(Label label) {
//		this.label = label;
//	}

	public Note() {
		super();
	}
   
	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Label> getListLabel() {
		return listLabel;
	}

	public void setListLabel(List<Label> listLabel) {
		this.listLabel = listLabel;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", userId=" + userId + ", title=" + title + ", description=" + description
				+ ", colorCode=" + colorCode + ", reminder=" + reminder + ", isPin=" + isPin + ", isArchive="
				+ isArchive + ", isTrash=" + isTrash + ", created=" + created + ", modified=" + modified
				+ ", listLabel=" + listLabel + "]";
	}


	 

	 

 

 

}
