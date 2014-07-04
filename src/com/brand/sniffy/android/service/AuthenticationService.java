package com.brand.sniffy.android.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.brand.sniffy.android.BackOfficeConstants;
import com.brand.sniffy.android.sync.ConnectionManager;
import com.brand.sniffy.android.sync.ConnectionResponse;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings.Secure;

public class AuthenticationService {
	
	public static final String SNIFFY_USER_TYPE =  "com.brand.sniffy.android.account";
	
	public static final String DEVICE_CLASS = "ANDROID";

	private static final String LAST_SYNCHRONIZATION_TIME = "lastSynchronizationTime";
	
	private String deviceUUID;
	
	private Context _context;
	
	private AccountManager accountManager;
	
	private ConnectionManager connectionManager;
	
	public AuthenticationService(Context context){
		this._context = context;
		this.accountManager = AccountManager.get(_context);
		this.connectionManager = new ConnectionManager(_context);
		this.deviceUUID  = Secure.getString(_context.getContentResolver(), Secure.ANDROID_ID);
		if(deviceUUID == null){
			throw new IllegalStateException("unknow device id");
		}
	}
	 
	public boolean authenticate(String login, String password){
		if(connectionManager.isOnline()){
			Map<String, String> headers = new HashMap<String, String>();
			headers.put(ConnectionManager.USER_LOGIN_HEADER, login);
			headers.put(ConnectionManager.USER_PASSWORD_HEADER, password);
			headers.put(ConnectionManager.DEVICE_UUID_HEADER, deviceUUID);
			
			String url = BackOfficeConstants.BO_URL + BackOfficeConstants.USER_SERVICE + BackOfficeConstants.AUTHENTICATION_METHOD;
			ConnectionResponse response = connectionManager.doPost(url, headers);
			if(HttpStatus.SC_OK == response.getStatus()){
				Account account = new Account(login, SNIFFY_USER_TYPE);
				String passwod = accountManager.getPassword(account);
				if(passwod == null){
					accountManager.addAccountExplicitly(account, password, null);
					ContentResolver.setSyncAutomatically(account, "com.brand.sniffy.android.syncdata.provider", true);
				}
				return true;
			}
			else if(HttpStatus.SC_FORBIDDEN == response.getStatus()){
				return this.registerDevice(login, password);
			}
			else if(HttpStatus.SC_UNAUTHORIZED == response.getStatus()){
				return false;
			}
			else{
				response.getStatus();
				response.getReason();
				return false;
			}
		}
		else{
			Account account = new Account(login, SNIFFY_USER_TYPE);
			String correctPassword = accountManager.getPassword(account);
			
			if(correctPassword != null){
				return correctPassword.equals(password);
			}
		}
		return false;
	}

	private boolean registerDevice(String login, String password) {
		if(connectionManager.isOnline()){
			String registerUrl = BackOfficeConstants.BO_URL + BackOfficeConstants.USER_SERVICE + BackOfficeConstants.REGISTER_DEVICE_METHOD;
			Map<String, String> headers = new HashMap<String, String>();
			headers.put(ConnectionManager.USER_LOGIN_HEADER, login);
			headers.put(ConnectionManager.USER_PASSWORD_HEADER, password);
			
			JSONObject request = new JSONObject();
			try {
				request.put("deviceClass", DEVICE_CLASS);
				request.put("uuid", deviceUUID);
			} catch (JSONException e) {
				throw new IllegalStateException(e);
			}
	
			ConnectionResponse registerResponse = connectionManager.doPost(registerUrl, request, headers);
			if(HttpStatus.SC_OK == registerResponse.getStatus()){
				return true;
			}
		}
		
		return false;
	}

	public Long getLastSynchronizationTime(Account account) {
		String time = accountManager.getUserData(account, LAST_SYNCHRONIZATION_TIME);
		if(time != null){
			return Long.parseLong(time);
		}
		return null;
	}

	public void saveSynchronizationTime(Account account, Long sychronizationTime) {
		accountManager.setUserData(account, LAST_SYNCHRONIZATION_TIME, sychronizationTime.toString());	
	}

	public boolean register(String login, String password) {
		if(connectionManager.isOnline()){
			String registerUrl = BackOfficeConstants.BO_URL + BackOfficeConstants.USER_SERVICE + BackOfficeConstants.REGISTER_USER_METHOD;
			Map<String, String> headers = new HashMap<String, String>();
			
			JSONObject request = new JSONObject();
			try {
				request.put("login", login);
				request.put("password", password);
			} catch (JSONException e) {
				throw new IllegalStateException(e);
			}
	
			ConnectionResponse registerResponse = connectionManager.doPost(registerUrl, request, headers);
			if(HttpStatus.SC_OK == registerResponse.getStatus()){
				boolean result = registerDevice(login, password);
				if(result){
					Account account = new Account(login, SNIFFY_USER_TYPE);
					accountManager.addAccountExplicitly(account, password, null);
					ContentResolver.setSyncAutomatically(account, "com.bpel4mobile.hotel.android.syncdata.provider", true);
				}
				
				return result;
			}
		}
		return false;
	}
	
	public String getDeviceUUID(){
		return deviceUUID;
	}
	
}
