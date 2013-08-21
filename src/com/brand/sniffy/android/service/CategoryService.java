package com.brand.sniffy.android.service;


import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

import com.brand.sniffy.android.model.Category;
import com.brand.sniffy.android.model.Database;


public class CategoryService {

	private Database database;
	
	public CategoryService(Context context){
		database = new Database(context);
	}
	
	public void applyChanges(JSONArray categories) throws SQLException, JSONException{
		for(int i =0 ; i< categories.length(); ++i){
			Category category =  new Category(categories.getJSONObject(i));
			
			Category existingCategory = getCategory(category.getId());
			if(existingCategory == null){
				database.getDao(Category.class).create(category);
			}
			else{
				database.getDao(Category.class).update(category);
			}
		}
	}

	private Category getCategory(int id) {
		try{
			List<Category> result  = database.getRuntimeExceptionDao(Category.class)
					.queryBuilder().where().eq(Category.ID_FIELD, id).query();
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
