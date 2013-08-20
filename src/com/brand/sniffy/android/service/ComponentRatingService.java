package com.brand.sniffy.android.service;


import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

import com.brand.sniffy.android.model.ComponentRating;
import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.model.Scanning;

public class ComponentRatingService {

	private Database database;
	
	public ComponentRatingService(Context context){
		database = new Database(context);
	}
	
	public void applyChanges(JSONArray ratings){
		
		for(int i =0 ; i< ratings.length(); ++i){
			try{
				ComponentRating rating = new ComponentRating(ratings.getJSONObject(i));
				ComponentRating existingRating = getComponentRating(rating.getId());
				if(existingRating == null){
					database.getRuntimeExceptionDao(ComponentRating.class).create(rating);
				}
				else{
					database.getRuntimeExceptionDao(ComponentRating.class).update(rating);
				}
			}catch(JSONException e){
				// TODO: log error
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
}
