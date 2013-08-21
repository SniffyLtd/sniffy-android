package com.brand.sniffy.android.service;


import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

import com.brand.sniffy.android.model.Country;
import com.brand.sniffy.android.model.Database;

public class CountryService {

	private Database database;
	
	public CountryService(Context context){
		database = new Database(context);
	}
	
	public void applyChanges(JSONArray countries) throws SQLException, JSONException{
		for(int i =0 ; i< countries.length(); ++i){
			Country country =  new Country(countries.getJSONObject(i));
			
			Country existingCountry = getCountry(country.getId());
			if(existingCountry == null){
				database.getDao(Country.class).create(country);
			}
			else{
				database.getDao(Country.class).update(country);
			}
		}
	}

	private Country getCountry(int id) {
		try{
			List<Country> result  = database.getDao(Country.class)
					.queryBuilder().where().eq(Country.ID_FIELD, id).query();
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
