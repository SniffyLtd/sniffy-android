package com.brand.sniffy.android.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

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
	
	public RemoteProductService(Context context){
		connectionManager = new ConnectionManager(context);
	}

	public SearchProductResult findProduct(String barcode) {
		
		if(!connectionManager.isOnline()){  
			return new SearchProductResult(SearchProductResult.ResultStatus.OFFLINE, null);
		}
		
		StringBuilder searchUrlBuilder = new StringBuilder(BackOfficeConstants.BO_URL);
		searchUrlBuilder.append(BackOfficeConstants.PRODUCT_SERVICE);
		searchUrlBuilder.append(String.format(BackOfficeConstants.FIND_BY_BARCODE_METHOD, barcode));
		
		Log.d(this.getClass().getName(), "Request url is: " +searchUrlBuilder.toString());
		
		ConnectionResponse response = connectionManager.doGet(searchUrlBuilder.toString());
		if(ConnectionResponse.OK_STATUS.equals(response.getStatus())){
			try {
				Product product = new Product(response.getResult().getJSONObject(PRODUCT_FIELD));
				return new SearchProductResult(SearchProductResult.ResultStatus.OK, product);
			} catch (JSONException e) {
				return new SearchProductResult(SearchProductResult.ResultStatus.INVALID_RESULT, null);
			}
		}
		else if(ConnectionResponse.NOT_FOUND_STATUS.equals(response.getStatus())){
			return new SearchProductResult(SearchProductResult.ResultStatus.NOT_FOUND, null);
		}
		else{
			Log.e(this.getClass().getName(), response.getReason().toString());
			return new SearchProductResult(SearchProductResult.ResultStatus.SERVER_ERROR, null);
		}
	}

}
