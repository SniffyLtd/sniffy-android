package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.model.Scanning;
import com.j256.ormlite.stmt.DeleteBuilder;

public class ScanningService {

	private Database database;
	
	public ScanningService(Context context){
		database = new Database(context);
	}
	
	public Scanning create(String barecode, Product  product){
		Scanning scanning = new Scanning();
		scanning.setBarecode(barecode);
		scanning.setDate(new Date());
		scanning.setFoundProduct(product);
		database.getRuntimeExceptionDao(Scanning.class).create(scanning);
		return scanning;
	}

	public List<Scanning> lastTeen() {
		try {
			return database.getRuntimeExceptionDao(Scanning.class).queryBuilder().orderBy(Scanning.DATE_FIELD, false).limit(10L).query();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Scanning>();
		}
	}

	public Scanning get(int id) {
		try {
			return database.getRuntimeExceptionDao(Scanning.class).queryBuilder().where().eq(Scanning.ID_FIELD, id).query().get(0);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void remove(int id) {
		try {
			DeleteBuilder<Scanning, ?> db = database.getRuntimeExceptionDao(Scanning.class).deleteBuilder();
			db.where().eq(Scanning.ID_FIELD, id);
			db.delete();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
