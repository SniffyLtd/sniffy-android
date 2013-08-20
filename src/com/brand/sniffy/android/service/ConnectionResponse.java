package com.brand.sniffy.android.service;

import org.json.JSONObject;
import org.json.JSONException;

public class ConnectionResponse {

	public static final String OK_STATUS = "ok";

	private static final String SERVER_ERROR = "serverError";

	private static final String CONNECTION_ERROR = null;

	private static final String PARSE_RESULT_ERROR = null;

	private static final String OTHER_HTTP_ERROR_ERROR = null;
	
	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public static ConnectionResponse createSuccessResponse(JSONObject result){
		ConnectionResponse response = new ConnectionResponse();
		response.status = OK_STATUS;
		response.result = result;
		return response;
	}
	public static ConnectionResponse createServerErrorResponse(JSONObject reason){
		ConnectionResponse response = new ConnectionResponse();
		response.status = SERVER_ERROR;
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
	public static ConnectionResponse createOtherErrorResponse(String reason){
		ConnectionResponse response = new ConnectionResponse();
		response.status = OTHER_HTTP_ERROR_ERROR;
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
	
}
