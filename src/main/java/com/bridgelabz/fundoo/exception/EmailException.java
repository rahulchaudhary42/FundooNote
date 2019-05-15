package com.bridgelabz.fundoo.exception;

//Email Exception Class
public class EmailException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private int errorCode;
	public EmailException(String message,int errorCode) {
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

