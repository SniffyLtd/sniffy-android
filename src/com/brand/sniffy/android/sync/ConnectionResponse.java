package com.brand.sniffy.android.sync;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.json.JSONException;

public class ConnectionResponse {

	public static final int CONNECTION_ERROR = 1;

	public static final int PARSE_RESULT_ERROR = 2;

	private int status;

	private JSONObject result;
	
	private JSONObject reason;
	
	public JSONObject getReason() {
		return reason;
	}

	public void setReason(JSONObject reason) {
		this.reason = reason;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject response) {
		this.result = response;
	}

	public int getStatus() {
		return status;
	}

	//public void setStatus(String status) {
//		this.status = status;
//	}
	
	public static ConnectionResponse createSuccessResponse(JSONObject result){
		ConnectionResponse response = new ConnectionResponse();
		response.status = HttpStatus.SC_OK;
		response.result = result;
		return response;
	}
	public static ConnectionResponse createServerErrorResponse(JSONObject reason){
		ConnectionResponse response = new ConnectionResponse();
		response.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
		response.reason = reason;
		return response;
	}

	public static ConnectionResponse createConnectionErrorResponse(String message) {
		ConnectionResponse response = new ConnectionResponse();
		response.status = CONNECTION_ERROR;
		response.reason = prepareReason(message);
		return response;
	}

	public static ConnectionResponse createParseErrorResponse() {
		ConnectionResponse response = new ConnectionResponse();
		response.status = PARSE_RESULT_ERROR;
		response.reason = prepareReason("Unable to parse http success response to JSON.");
		return response;
	}
	public static ConnectionResponse createOtherErrorResponse(String reason, int status){
		ConnectionResponse response = new ConnectionResponse();
		response.status = status;
		response.reason = prepareReason(reason);
		return response;
	}

	private static JSONObject prepareReason(String message) {
		try{
			JSONObject reason = new JSONObject();
			reason.put("message", message);
			return reason;
		}
		catch (JSONException e){			
			return null;	
		}
	}

	public static ConnectionResponse createNotFountErrorResponse() {
		ConnectionResponse response = new ConnectionResponse();
		response.status = HttpStatus.SC_NOT_FOUND;
		response.reason = prepareReason("Request parameter not found.");
		return response;
	}

	public static ConnectionResponse createForbiddenResponse() {
		ConnectionResponse response = new ConnectionResponse();
		response.status = HttpStatus.SC_FORBIDDEN;
		return response;
	}

	public static ConnectionResponse createUnauthorizedResponse() {
		ConnectionResponse response = new ConnectionResponse();
		response.status = HttpStatus.SC_UNAUTHORIZED;
		return response;
	}
	
}
