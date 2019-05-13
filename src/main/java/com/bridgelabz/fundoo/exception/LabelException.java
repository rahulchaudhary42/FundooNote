package com.bridgelabz.fundoo.exception;

public class LabelException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	public LabelException(String message , int errorCode) {
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
