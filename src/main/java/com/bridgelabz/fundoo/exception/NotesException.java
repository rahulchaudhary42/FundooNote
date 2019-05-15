package com.bridgelabz.fundoo.exception;

// Node Exception Class
public class NotesException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private int errorCode;
	public NotesException(String message , int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
