package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.brand.sniffy.android.model.Component;
import com.brand.sniffy.android.model.ComponentRating;
import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.model.Scanning;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class ComponentsService {
	
	private Database database;
	
	public ComponentsService(Context context){
		database = new Database(context);
	}
	
	public void applyChanges(JSONArray components){
		try{
			for(int i =0 ; i< components.length(); ++i){
				Component component =  new Component(components.getJSONObject(i));
				component = resolveRating(component);
				
				Component existingComponent = getComponent(component.getId());
				if(existingComponent == null){
					database.getRuntimeExceptionDao(Component.class).create(component);
				}
				else{
					database.getRuntimeExceptionDao(Component.class).update(component);
				}
			}
		}
		catch(JSONException e){
			// TODO: log error
		}
	}
	
	private Component resolveRating(Component component) {
		try{
			List<ComponentRating> result  = database.getRuntimeExceptionDao(ComponentRating.class).queryBuilder().where().eq(Scanning.ID_FIELD, component.getRating().getId()).query();
			if(result.size() > 0){
				component.setRating(result.get(0));
			}
		}catch(SQLException e){
			// TODO : log error
		}
		return component;
	}

	private Component getComponent(int id) {
		try{
			List<Component> result  = database.getRuntimeExceptionDao(Component.class).queryBuilder().where().eq(Scanning.ID_FIELD, id).query();
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

	public List<Component> getProductComponents(Product product){
		//try{
			List<Component> components = database.getRuntimeExceptionDao(Component.class).queryForAll();
			for(Component component : components){
				resolveRating(component);
			}
			return components;
	//	}catch(SQLException e){
		//	return new ArrayList<Component>();
	//	}
	}
	
	
}
