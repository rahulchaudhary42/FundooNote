package com.bridgelabz.fundoo.exception;

// Registration Exception Class
public class RegistrationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private int errorCode;
	public RegistrationException(String message , int errorCode) {
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
