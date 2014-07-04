package com.brand.sniffy.android.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import com.brand.sniffy.android.BackOfficeConstants;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.sync.ConnectionManager;
import com.brand.sniffy.android.sync.ConnectionResponse;

public class RemoteProductService {
	
	private static final String PRODUCT_FIELD = "product";

	List<Product> testProducts = new ArrayList<Product>();
	
	private ConnectionManager connectionManager;

	private Account account;
	
	private AccountManager accountManager;
	
	private AuthenticationService authenticationService;
	
	public RemoteProductService(Context context, Account account){
		this.account = account;
		this.accountManager = AccountManager.get(context);
		this.connectionManager = new ConnectionManager(context);
		this.authenticationService = new AuthenticationService(context);
	}

	public Product findProduct(String barcode) {
		
		if(!connectionManager.isOnline()){
			throw new IllegalStateException("No internet connection.");
		}
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(ConnectionManager.USER_LOGIN_HEADER, account.name);
		headers.put(ConnectionManager.USER_PASSWORD_HEADER, accountManager.getPassword(account));
		headers.put(ConnectionManager.DEVICE_UUID_HEADER, authenticationService.getDeviceUUID());
		
		JSONObject request = new JSONObject();
		try {
			request.put("barcode", barcode);
			request.put("requestDate", new Date().getTime());
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		
		StringBuilder searchUrlBuilder = new StringBuilder(BackOfficeConstants.BO_URL);
		searchUrlBuilder.append(BackOfficeConstants.SEARCH_SERVICE);
		
		Log.d(this.getClass().getName(), "Request url is: " + searchUrlBuilder.toString());
		
		ConnectionResponse response = connectionManager.doPost(searchUrlBuilder.toString(), request, headers);
		if(HttpStatus.SC_OK == response.getStatus()){
			try {
				JSONObject searchResult = response.getResult();
				if(searchResult.has(PRODUCT_FIELD)){
					Product product = new Product(searchResult.getJSONObject(PRODUCT_FIELD));
					return product;
				}
				else{
					return null;
				}
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
		else if(HttpStatus.SC_BAD_REQUEST == response.getStatus()){
			try {
				throw new IllegalArgumentException(response.getReason().getString("message"));
			} catch (JSONException e) {
				throw new IllegalArgumentException(e);
			}
		}
		else{
			throw new RuntimeException("Error http status is:" + response.getStatus());
		}
	}

}
