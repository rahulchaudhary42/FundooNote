package com.bridgelabz.fundoo.exception;

 
	
	public class JWTTokenException extends RuntimeException{
		private static final long serialVersionUID = 1L;	
		private int statusCode;
		
		
		
		public int getStatusCode() {
			return statusCode;
		}



		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}



		public JWTTokenException(String message,int statusCode) {
			super(message);
			this.statusCode = statusCode;
		}
	}
 
