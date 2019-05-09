package com.bridgelabz.fundoo.util;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;

public class StatusHelper {
	
	public static Response statusInfo(String statusMessage, int statusCode){
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
	
		return response;
	}
	
	public static ResponseToken tokenStatusInfo(String statusMessage,int statusCode,String token){
		ResponseToken tokenResponse = new ResponseToken();
		tokenResponse.setStatusCode(statusCode);
		tokenResponse.setStatusMessage(statusMessage);
		tokenResponse.setToken(token);

		return tokenResponse;
	}
	public static ResponseToken statusResponseInfo(String statusMessage, int statusCode) {
		ResponseToken response = new ResponseToken();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		
		return response;
	}

}
