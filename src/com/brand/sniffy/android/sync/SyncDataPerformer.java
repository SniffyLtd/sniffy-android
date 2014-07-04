package com.brand.sniffy.android.sync;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.model.Scanning;
import com.brand.sniffy.android.service.ComponentRatingService;
import com.brand.sniffy.android.service.ComponentsService;
import com.brand.sniffy.android.service.ProductService;
import com.brand.sniffy.android.service.ScanningService;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncStats;

public class SyncDataPerformer {

	private ComponentRatingService componentRatingService;
	
	private ComponentsService componentsService;
	
	private ProductService productService;
	
	private ScanningService scanningService;

	public SyncDataPerformer(Context context, Account account) {
		this.componentRatingService = new ComponentRatingService(context, account);
		this.componentsService = new ComponentsService(context, account);
		this.productService = new ProductService(context, account);
		this.scanningService = new ScanningService(context, account);
	}
	
	public void processSyncResponse(JSONObject synchronizationData, SyncStats stats){
		try {
			JSONArray componentRatingsToUpdate = synchronizationData.getJSONArray("componentRatingsToUpdate");
			componentRatingService.applyChanges(componentRatingsToUpdate, stats);
			JSONArray componentsToUpdate = synchronizationData.getJSONArray("componentsToUpdate");
			componentsService.applyChanges(componentsToUpdate, stats);
			
			JSONArray productsToUpdate = synchronizationData.getJSONArray("productsToUpdate");
			productService.applyChanges(productsToUpdate, stats);
			JSONArray searchResults = synchronizationData.getJSONArray("searchResults");
			processSearchResults(searchResults);
			JSONArray  componentsToDelete = synchronizationData.getJSONArray("componentsToDelete");
			componentsService.deleteComponents(componentsToDelete, stats);
			JSONArray componentRatingsToDelete = synchronizationData.getJSONArray("componentRatingsToDelete");
			componentRatingService.deleteRatings(componentRatingsToDelete, stats);
		} catch (JSONException e) {
			throw SynchronizationException.invalidDataError("Error while processing synchronization data.", e);
		} catch (SQLException e) {
			throw SynchronizationException.databaseError("Error while processing synchronization data.",e);
		}
	}

	private void processSearchResults(JSONArray searchResults) throws JSONException {
		for(int i =0; i< searchResults.length(); ++i){
			JSONObject searchResult = searchResults.getJSONObject(i);
			Product product = new Product(searchResult.getJSONObject("product"));
			productService.save(product);
		}
	}

	public JSONArray getSearchRequestToSync() {
		List<Scanning> incomplitedScannings = scanningService.findIncomplitedScannings();
		JSONArray array = new JSONArray();
		for(Scanning scanning : incomplitedScannings){
			JSONObject json = new JSONObject();
			try {
				json.put("barcode", scanning.getBarecode());
				json.put("requestDate", scanning.getDate());

			} catch (JSONException e) {
				throw SynchronizationException.invalidDataError("unable to create search request json object.", e);
			}
			array.put(json);
		}
		return array;
	}
}
