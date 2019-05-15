package com.bridgelabz.fundoo.exception;

// Login Exception Class
public class LoginException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	public LoginException(String message,int errorCode) {
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
