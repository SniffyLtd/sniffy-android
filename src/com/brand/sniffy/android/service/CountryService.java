package com.brand.sniffy.android.service;


import java.sql.SQLException;
import java.util.List;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.brand.sniffy.android.model.Country;
import com.brand.sniffy.android.model.Database;

public class CountryService {

	private Database database;
	
	public CountryService(Context context, Account account){
		database = new Database(context, account);
	}

	private Country get(int id) {
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
			throw new IllegalStateException(e);
		}
	}

	public void save(Country country) {
		Country existingCountry = get(country.getId());
		if(existingCountry != null){
			update(country);
		}
		else{
			create(country);
		}
	}

	private void update(Country country) {
		database.getRuntimeExceptionDao(Country.class).update(country);
		Log.d(this.getClass().getName(), String.format("Country %d updated.", country.getId()));
	}

	private void create(Country country) {
		database.getRuntimeExceptionDao(Country.class).create(country);	
		Log.d(this.getClass().getName(), String.format("Country %d created.", country.getId()));
	}
}
