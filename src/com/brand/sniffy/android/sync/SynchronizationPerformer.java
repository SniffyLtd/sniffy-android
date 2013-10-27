package com.brand.sniffy.android.sync;

import java.sql.SQLException;
import java.util.Date;


import android.os.AsyncTask;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.SynchronizationHistory;
import com.brand.sniffy.android.service.CategoryService;
import com.brand.sniffy.android.service.ComponentRatingService;
import com.brand.sniffy.android.service.ComponentsService;
import com.brand.sniffy.android.service.CountryService;

public class SynchronizationPerformer extends AsyncTask<String, SynchronizationStatus, SynchronizationPostExecuteParameter> {
	// TODO: add logs 

	public static final String SYNCHRONIZATION_STATUS_BROADCAST = "com.brand.sniffy.Synchronization";
	
	private static final String COMPONENT_RATINGS_FIELD = "componentRatings";

	private static final String COMPONENTS_FIELD = "components";

	private static final String COUNTRIES_FIELD = "conutries";

	private static final String CATEGORIES_FIELD = "categories";

	private static final String SYNCHRONIZATION_TIME_FIELD = "synchronizationTime";

	private static final String RESULT_FIELD = "result";

	private Context context;
	
	private ConnectionManager connectionManager;
	
	private ComponentsService componentsService;
	
	private ComponentRatingService componentRatingService;
	
	private CategoryService categoryService;
	
	private CountryService countryService;

	private Database database;
	
	public SynchronizationPerformer(Context context){
		this.context = context;
		database = new Database(context);
		this.connectionManager = new ConnectionManager(context);
		this.componentsService = new ComponentsService(context);
		this.componentRatingService = new ComponentRatingService(context);
		this.categoryService = new CategoryService(context);
		this.countryService = new CountryService(context);
	}
	
	 protected SynchronizationPostExecuteParameter doInBackground(String... urls) {
		 if(urls.length != 1){
			 throw new IllegalArgumentException("Urls array have to have single element.");
		 }
		 
		 publishProgress(SynchronizationStatus.STARTED);
		 ConnectionResponse response = connectionManager.doGet(urls[0]);
		 if(ConnectionResponse.OK_STATUS.equals(response.getStatus())){
			 publishProgress(SynchronizationStatus.APLAYING_CHANGES);
			 try{
	 			 JSONObject result = response.getResult();
	 			 JSONObject syncResult = result.getJSONObject(RESULT_FIELD);
	 			 
	 			JSONArray componentRatings = syncResult.getJSONArray(COMPONENT_RATINGS_FIELD);
	 			if(componentRatings != null && componentRatings.length() > 0 ){
	 				componentRatingService.applyChanges(componentRatings);
	 			}
	 			 
	 			JSONArray components = syncResult.getJSONArray(COMPONENTS_FIELD);
	 			if(components != null && components.length()>0){
	 				componentsService.applyChanges(components);
	 			}

	 			 JSONArray countries = syncResult.getJSONArray(COUNTRIES_FIELD);
	 			 if(countries != null && countries.length() > 0 ){
	 				 countryService.applyChanges(countries);
	 			 }
	 			 
	 			 JSONArray categories = syncResult.getJSONArray(CATEGORIES_FIELD);
	 			 if(categories != null && categories.length() > 0 ){
	 				 categoryService.applyChanges(categories);
	 			 }
	 			 
				 publishProgress(SynchronizationStatus.FINISHED_WITH_SUCCESS);
				 return new SynchronizationPostExecuteParameter(SynchronizationStatus.FINISHED_WITH_SUCCESS, result.getLong(SYNCHRONIZATION_TIME_FIELD));
			 }
			 catch(JSONException e){
				 publishProgress(SynchronizationStatus.APLAYING_SYNC_DATA_ERROR);
				 return new SynchronizationPostExecuteParameter(SynchronizationStatus.APLAYING_SYNC_DATA_ERROR);
			 }
			 catch(SQLException e){
				 publishProgress(SynchronizationStatus.APLAYING_SYNC_DATA_ERROR);
				 return new SynchronizationPostExecuteParameter(SynchronizationStatus.APLAYING_SYNC_DATA_ERROR);
			 }
		 }
		 else{
			 Log.e(this.getClass().getName(), String.format("Synchronization finished with error: %s- %s", response.getStatus(), response.getReason().toString()));
			 publishProgress(SynchronizationStatus.FINISHED_WITH_ERROR);
			 return new SynchronizationPostExecuteParameter(SynchronizationStatus.FINISHED_WITH_ERROR);
		 }
	 }
	 
	 protected void onProgressUpdate(SynchronizationStatus... status) {
		 Intent broadcastIntent = new Intent();
		 broadcastIntent.setAction(SYNCHRONIZATION_STATUS_BROADCAST);
		 broadcastIntent.putExtra("status", status[0].toString());
         context.sendBroadcast(broadcastIntent);
     }

     protected void onPostExecute(SynchronizationPostExecuteParameter result) {
    	 SynchronizationHistory historyRecord = new SynchronizationHistory();
    	 String status;
    	 Long syncTime;
    	 if(SynchronizationStatus.FINISHED_WITH_SUCCESS.equals(result.getStatus())){
    		 status = SynchronizationHistory.SUCCESS_STATUS;
    		 syncTime = result.getSynchronizationTime();
    	 }
    	 else if(SynchronizationStatus.FINISHED_WITH_ERROR.equals(result.getStatus())){
    		 status = SynchronizationHistory.BO_INTERNAL_ERROR_STATUS;
    		 syncTime = new Date().getTime();
    	 }
    	 else{
    		 status = SynchronizationHistory.OTHER_ERROR_STATUS;
    		 syncTime = new Date().getTime();
    	 }
		 historyRecord.setStatus(status);
		 historyRecord.setSynchronizationTime(syncTime);
		 database.getRuntimeExceptionDao(SynchronizationHistory.class).create(historyRecord);
     }
     
}
