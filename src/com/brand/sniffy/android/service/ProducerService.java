package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.List;

import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Producer;

import android.accounts.Account;
import android.content.Context;

public class ProducerService {

	private Database database; 
	
	private CountryService countryService;
	
	public ProducerService(Context context, Account account) {
		database = new Database(context, account);
		countryService = new CountryService(context, account);
	}

	public void save(Producer producer) {
		Producer existingProducer = get(producer.getId());
		if(existingProducer != null){
			update(producer);
		}
		else{
			create(producer);
		}
	}

	private void create(Producer producer) {
		if(producer.getCountry() != null){
			countryService.save(producer.getCountry());
		}
		database.getRuntimeExceptionDao(Producer.class).create(producer);
	}

	private void update(Producer producer) {
		if(producer.getCountry() != null){
			countryService.save(producer.getCountry());
		}
		database.getRuntimeExceptionDao(Producer.class).update(producer);
	}

	private Producer get(int id) {
		try{
			List<Producer> result  = database.getRuntimeExceptionDao(Producer.class)
					.queryBuilder().where().eq(Producer.ID_FIELD, id).query();
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

}
