package com.brand.sniffy.android.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.brand.sniffy.android.BackOfficeConstants;
import com.brand.sniffy.android.sync.ConnectionManager;
import com.brand.sniffy.android.sync.ConnectionResponse;
import com.brand.sniffy.android.sync.SyncDataPerformer;
import com.brand.sniffy.android.sync.SynchronizationException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SyncStats;
import android.util.Log;

public class SynchronizationService {
	
	private Context context;
	
	private ConnectionManager connectionManager;
	
	private AuthenticationService authenticationService;
	
	private AccountManager accountManager;
	
	public SynchronizationService(Context context){
		this.context = context;
		this.authenticationService = new AuthenticationService(context);
		this.connectionManager = new ConnectionManager(context);
		this.accountManager = AccountManager.get(context);
	}
	
	public void requestSynchronization(Account account, SyncStats stats){
		if(connectionManager.isOnline()){
				Long lastSynchronizationTime = getLastSuccessSynchronizationTime(account);
				SyncDataPerformer syncDataPerformer = new SyncDataPerformer(context, account);
				
				if(lastSynchronizationTime == null){
					lastSynchronizationTime = 0L;
				}
				Log.d(SynchronizationService.class.getName(), String.format("Synchronization started, %s", account.name));
				String synchronizationRequestUrl = BackOfficeConstants.BO_URL + BackOfficeConstants.SYNCHRONIZATION_SERVICE;
				
				Map<String, String> headers = new HashMap<String, String>();
				headers.put(ConnectionManager.USER_LOGIN_HEADER, account.name);
				headers.put(ConnectionManager.USER_PASSWORD_HEADER, accountManager.getPassword(account));
				headers.put(ConnectionManager.DEVICE_UUID_HEADER, authenticationService.getDeviceUUID());
				
				JSONObject request = new JSONObject();
				try {
					request.put("lastSynchronizationDate", lastSynchronizationTime);
					request.put("searchRequests", syncDataPerformer.getSearchRequestToSync());
				} catch (JSONException e) {
					throw new IllegalStateException(e);
				}
				
				ConnectionResponse response = connectionManager.doPost(synchronizationRequestUrl, request, headers);
				if(HttpStatus.SC_OK == response.getStatus()){
					try {
						
						syncDataPerformer.processSyncResponse(response.getResult(), stats);
						authenticationService.saveSynchronizationTime(account, response.getResult().getLong("synchronizationDate"));
					} catch (JSONException e) {
						throw SynchronizationException.invalidDataError("Can't read synchronization response.", e);
					}
				}
				else if(HttpStatus.SC_UNAUTHORIZED == response.getStatus()){
					throw SynchronizationException.authrorizationError("User credentials failed.");
				}
				else{
					throw SynchronizationException.connectionError("Server error.", null);
				}
			}
	}

	

	private Long getLastSuccessSynchronizationTime(Account account) {
		return authenticationService.getLastSynchronizationTime(account);
	}
}
