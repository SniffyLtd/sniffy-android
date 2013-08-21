package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.List;

import com.brand.sniffy.android.model.Component;
import com.brand.sniffy.android.model.ComponentRating;
import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Product;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

public class ComponentsService {
	
	private Database database;
	
	public ComponentsService(Context context){
		database = new Database(context);
	}
	
	public void applyChanges(JSONArray components) throws JSONException, SQLException{
			for(int i =0 ; i< components.length(); ++i){
				Component component =  new Component(components.getJSONObject(i));
				component = resolveRating(component);
				
				Component existingComponent = getComponent(component.getId());
				if(existingComponent == null){
					database.getDao(Component.class).create(component);
				}
				else{
					database.getDao(Component.class).update(component);
				}
			}
	}
	
	private Component resolveRating(Component component) throws SQLException {
			List<ComponentRating> result  = database.getDao(ComponentRating.class)
					.queryBuilder().where().eq(Component.ID_FIELD, component.getRating().getId()).query();
			if(result.size() > 0){
				component.setRating(result.get(0));
			}
		
		return component;
	}

	private Component getComponent(int id) {
		try{
			List<Component> result  = database.getRuntimeExceptionDao(Component.class).queryBuilder().where().eq(Component.ID_FIELD, id).query();
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
			//for(Component component : components){
			//	try {
					//resolveRating(component);
			//	} catch (SQLException e) {
					// TODO: log error
			//	}
			//}
			return components;
	//	}catch(SQLException e){
		//	return new ArrayList<Component>();
	//	}
	}
	
	public void joinProductWithComponent(Product product, Component component){
		
	}
	
	
}
