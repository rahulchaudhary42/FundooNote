package com.bridgelabz.fundoo.response;

import org.springframework.stereotype.Component;

   //Response class 
	@Component
	public class Response {
		
		private String statusMessage;	
		private int statusCode;
		public String getStatusMessage() {
			return statusMessage;
		}
		public void setStatusMessage(String statusMessage) {
			this.statusMessage = statusMessage;
		}
		public int getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}
		
	}


