package com.brand.sniffy.android.service;


import java.sql.SQLException;
import java.util.List;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncStats;

import org.json.JSONArray;
import org.json.JSONException;

import com.brand.sniffy.android.model.ComponentRating;
import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Scanning;
import com.j256.ormlite.stmt.DeleteBuilder;

public class ComponentRatingService {

	private Database database;
	
	public ComponentRatingService(Context context, Account account){
		database = new Database(context, account);
	}
	
	public void applyChanges(JSONArray ratings, SyncStats stats) throws JSONException, SQLException{
		
		for(int i =0 ; i< ratings.length(); ++i){
			ComponentRating rating = new ComponentRating(ratings.getJSONObject(i));
			ComponentRating existingRating = getComponentRating(rating.getId());
			if(existingRating == null){
				create(rating);
				stats.numInserts++;
			}
			else{
				update(rating);
				stats.numUpdates++;
			}
		}
	}

	private ComponentRating getComponentRating(int id) {
		try{
			List<ComponentRating> result  = database.getRuntimeExceptionDao(ComponentRating.class).queryBuilder().where().eq(Scanning.ID_FIELD, id).query();
			if(result.size() > 0){
				return result.get(0);
			}
			else{
				return null;
			}
		}catch(SQLException e){
			return null;
		}
	}

	public void deleteRatings(JSONArray componentRatingsToDelete, SyncStats stats) throws JSONException, SQLException {
		for(int i =0; i < componentRatingsToDelete.length(); ++i){
			Long id = componentRatingsToDelete.getLong(i);
			DeleteBuilder<ComponentRating, ?> db = database.getRuntimeExceptionDao(ComponentRating.class).deleteBuilder();
			db.where().eq(ComponentRating.ID_FIELD, id);
			db.delete();
			stats.numDeletes++;
		}
	}

	public void save(ComponentRating rating) {
		ComponentRating existingRating = getComponentRating(rating.getId());
		if(existingRating == null){
			create(rating);
		}
		else{
			update(rating);
		}
	}

	private void update(ComponentRating rating) {
		database.getRuntimeExceptionDao(ComponentRating.class).update(rating);	
	}

	private void create(ComponentRating rating) {
		database.getRuntimeExceptionDao(ComponentRating.class).create(rating);
	}
}
