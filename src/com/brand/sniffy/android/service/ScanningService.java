package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.model.Scanning;
import com.brand.sniffy.android.sync.SynchronizationException;
import com.j256.ormlite.stmt.DeleteBuilder;

public class ScanningService {

	private Database database;
	
	private AuthenticationService authenticationService;

	public ScanningService(Context context, Account account){
		database = new Database(context, account);
		authenticationService = new AuthenticationService(context);
	}
	
	public Scanning create(String barecode, Product  product, String status){
		Scanning scanning = new Scanning();
		scanning.setBarecode(barecode);
		scanning.setDate(new Date());
		scanning.setFoundProduct(product);
		scanning.setStatus(status);
		scanning.setDeviceClass(AuthenticationService.DEVICE_CLASS);
		scanning.setDeviceUUID(authenticationService.getDeviceUUID());
		database.getRuntimeExceptionDao(Scanning.class).create(scanning);
		Log.d(this.getClass().getName(), String.format("Scanning %d saved.", scanning.getId()));
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
			return database.getRuntimeExceptionDao(Scanning.class).queryBuilder()
					.where().eq(Scanning.ID_FIELD, id).query().get(0);
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

	public List<Scanning> findIncomplitedScannings() {
		try {
			return database.getRuntimeExceptionDao(Scanning.class).queryBuilder().where()
					.eq(Scanning.STATUS_FIELD, Scanning.STATUS_FAILED)
					.or()
					.eq(Scanning.STATUS_FIELD, Scanning.STATUS_PENDING).query();
		} catch (SQLException e) {
			throw SynchronizationException.databaseError(e.getMessage(), e);
		}
	}

	public void updateResults(Product product) {
		try {
			List<Scanning> scannings = database.getRuntimeExceptionDao(Scanning.class).queryBuilder().where()
			.eq(Scanning.BARECODE_FIELD, product.getBarcode()).query();
			
			for(Scanning scanning : scannings){
				scanning.setFoundProduct(product);
				scanning.setStatus(Scanning.STATUS_FOUND);
				update(scanning);
			}
		} catch (SQLException e) {
			throw SynchronizationException.databaseError(e.getMessage(), e);
		}
	}

	public void update(Scanning scanning) {
		database.getRuntimeExceptionDao(Scanning.class).update(scanning);
		Log.d(this.getClass().getName(), String.format("Scanning %d updated.", scanning.getId()));
	}
}
