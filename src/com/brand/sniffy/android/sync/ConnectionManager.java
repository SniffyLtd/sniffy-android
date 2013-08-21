package com.brand.sniffy.android.sync;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.StatusLine;
import org.apache.http.HttpStatus;


import org.json.JSONObject;
import org.json.JSONException;

public class ConnectionManager {

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
		        	return ConnectionResponse.createOtherErrorResponse(statusLine.getReasonPhrase());
		        }
		    }
		    else if(statusLine.getStatusCode() == HttpStatus.SC_NOT_FOUND){
		    	return ConnectionResponse.createNotFountErrorResponse();
		    }
		    else{
		    	return ConnectionResponse.createOtherErrorResponse(statusLine.getReasonPhrase());
		    }
		}
		catch(IOException e){
        	return ConnectionResponse.createConnectionErrorResponse(e.getMessage());
		}
	}
}
