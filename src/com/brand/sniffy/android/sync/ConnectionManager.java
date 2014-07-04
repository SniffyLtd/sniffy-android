package com.brand.sniffy.android.sync;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.StatusLine;
import org.apache.http.HttpStatus;


import org.json.JSONObject;
import org.json.JSONException;

public class ConnectionManager {

	public static final String USER_LOGIN_HEADER = "user-login";
	
	public static final String DEVICE_UUID_HEADER = "device-uuid";
	
	public static final String USER_PASSWORD_HEADER = "user-password";
	
	private Context context;
	
	public ConnectionManager(Context context){
		this.context = context;
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}

	public ConnectionResponse doGet(String url) {
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			get.setHeader("Content-Type", "application/json");
		    HttpResponse response = httpclient.execute(get);
		    StatusLine statusLine = response.getStatusLine();
		    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        out.close();
		        try{
		        	JSONObject result = new JSONObject(out.toString());
		        	return ConnectionResponse.createSuccessResponse(result);
		        } catch(JSONException e){
		        	return ConnectionResponse.createParseErrorResponse();
		        }
		    } else if(statusLine.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR){
		    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        out.close();
		        try{
		        	JSONObject reason = new JSONObject(out.toString());
		        	return ConnectionResponse.createServerErrorResponse(reason);
		        } catch(JSONException e){
		        	return ConnectionResponse.createOtherErrorResponse(statusLine.getReasonPhrase(), statusLine.getStatusCode());
		        }
		    }
		    else if(statusLine.getStatusCode() == HttpStatus.SC_NOT_FOUND){
		    	return ConnectionResponse.createNotFountErrorResponse();
		    }
		    else{
		    	return ConnectionResponse.createOtherErrorResponse(statusLine.getReasonPhrase(), statusLine.getStatusCode());
		    }
		}
		catch(IOException e){
        	return ConnectionResponse.createConnectionErrorResponse(e.getMessage());
		}
	}

	public ConnectionResponse doPost(String url, Map<String, String> headers) {
		return doPost(url, null, headers);
	}

	public ConnectionResponse doPost(String url, JSONObject request,Map<String, String> headers) {
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			if(request != null){
				post.setEntity(new ByteArrayEntity(request.toString().getBytes("UTF8")));
			}
			for(String name : headers.keySet()){
				post.setHeader(name, headers.get(name));
			}
			post.setHeader("Content-Type", "application/json");
			post.setHeader("Cache-Control","no-cache");
			
			
			Log.d(ConnectionManager.class.getName(), String.format("Executing post request, url: %s",  url));
			HttpResponse response = httpclient.execute(post);
		    StatusLine statusLine = response.getStatusLine();
		    Log.d(ConnectionManager.class.getName(), String.format("Response status is '%d'",  statusLine.getStatusCode()));
		    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        out.close();
		        try{
		        	JSONObject result = new JSONObject(out.toString());
		        	return ConnectionResponse.createSuccessResponse(result);
		        } catch(JSONException e){
		        	return ConnectionResponse.createParseErrorResponse();
		        }
		    } else if(statusLine.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR){
		    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        out.close();
		        try{
		        	JSONObject reason = new JSONObject(out.toString());
		        	return ConnectionResponse.createServerErrorResponse(reason);
		        } catch(JSONException e){
		        	return ConnectionResponse.createOtherErrorResponse(statusLine.getReasonPhrase(),statusLine.getStatusCode());
		        }
		    }
		    else if(statusLine.getStatusCode() == HttpStatus.SC_NOT_FOUND){
		    	return ConnectionResponse.createNotFountErrorResponse();
		    }
		    else if(statusLine.getStatusCode() == HttpStatus.SC_UNAUTHORIZED){
		    	return ConnectionResponse.createUnauthorizedResponse();
		    }
		    else if(statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN){
		    	return ConnectionResponse.createForbiddenResponse();
		    }
		    else{
		    	return ConnectionResponse.createOtherErrorResponse(statusLine.getReasonPhrase(),statusLine.getStatusCode());
		    }
			
		}
		catch(IOException e){
        	return ConnectionResponse.createConnectionErrorResponse(e.getMessage());
		}
	}
}
