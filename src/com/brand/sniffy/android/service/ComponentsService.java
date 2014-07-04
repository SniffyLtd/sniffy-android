package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.List;

import com.brand.sniffy.android.model.Component;
import com.brand.sniffy.android.model.ComponentRating;
import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.model.ProductComponents;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncStats;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class ComponentsService {
	
	private Database database;
	
	private ComponentRatingService componentRatingService;
	
	public ComponentsService(Context context, Account account){
		database = new Database(context, account);
		componentRatingService = new ComponentRatingService(context, account);
	}
	
	public void applyChanges(JSONArray components, SyncStats stats) throws JSONException, SQLException{
		for(int i =0 ; i< components.length(); ++i){
			Component component =  new Component(components.getJSONObject(i));
			component = resolveRating(component);
				
			Component existingComponent = getComponent(component.getId());
			if(existingComponent == null){
				create(component);
				stats.numInserts++;
			}
			else{
				update(component);
				stats.numUpdates++;
			}
		}
	}
	
	private void update(Component component) {
		if(component.getRating() != null){
			componentRatingService.save(component.getRating());
		}
		database.getRuntimeExceptionDao(Component.class).update(component);

		Log.d(this.getClass().getName(), String.format("Component %d updated.", component.getId()));
	}

	private void create(Component component) {
		if(component.getRating() != null){
			componentRatingService.save(component.getRating());
		}
		int id = database.getRuntimeExceptionDao(Component.class).create(component);
		component.setId(id);

		Log.d(this.getClass().getName(), String.format("Component %d created.", component.getId()));
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

	public List<Component> getProductComponents(Product product) throws SQLException{
		//try{
		//QueryBuilder<ProductComponents, ?> qb = database.getRuntimeExceptionDao(ProductComponents.class).queryBuilder();
		//qb.where().eq(ProductComponents.PRODUCT_FIELD, product);
		QueryBuilder<Component, ?> builder = database.getRuntimeExceptionDao(Component.class).queryBuilder();
		//builder.where().in(Component.ID_FIELD, );
		List<Component> components = builder.query();
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
	

	public void deleteComponents(JSONArray componentsToDelete, SyncStats stats) throws SQLException, JSONException {
		for(int i =0; i < componentsToDelete.length(); ++i){
			Long componentId = componentsToDelete.getLong(i);
			DeleteBuilder<ProductComponents, ?> db = database.getRuntimeExceptionDao(ProductComponents.class).deleteBuilder();
			db.where().eq(ProductComponents.COMPONENT_FIELD, componentId);
			db.delete();
			
			DeleteBuilder<Component, ?> db1 = database.getRuntimeExceptionDao(Component.class).deleteBuilder();
			db1.where().eq(Component.ID_FIELD, componentId);
			db1.delete();
			stats.numDeletes++;
		}
	}

	public void save(Component component) {
		Component existingComponent = getComponent(component.getId());
		if(existingComponent == null){
			create(component);
		}
		else{
			update(component);
		}
	}
	
	
}
