package com.bridgelabz.fundoo.exception;
// Token Exception Class
public class TokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private int errorCode;
	public TokenException(String message,int errorCode) {
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
