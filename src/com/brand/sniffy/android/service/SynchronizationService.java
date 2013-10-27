package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.brand.sniffy.android.BackOfficeConstants;
import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.SynchronizationHistory;
import com.brand.sniffy.android.sync.ConnectionManager;
import com.brand.sniffy.android.sync.SynchronizationPerformer;
import com.j256.ormlite.stmt.QueryBuilder;

import android.content.Context;
import android.util.Log;

public class SynchronizationService {
	
	private static final Long TWENTY_FOUR_HOURS = 1L;// 900000L;
												 
	
	private Database database;
	
	private ConnectionManager connectionManager;
	
	private SynchronizationPerformer synchronizationPerformer;
	
	
	public SynchronizationService(Context context){
		database = new Database(context);
		connectionManager = new ConnectionManager(context);
		synchronizationPerformer = new SynchronizationPerformer(context);
	}
	
	public void requestSynchronization(){
		if(isSynchronizationRequired()){
			if(connectionManager.isOnline()){
				Long lastSynchronizationTime = getLastSuccessSynchronizationTime();
				String synchronizationRequestUrl = prepareSynchronizationRequestUrl(lastSynchronizationTime);
				synchronizationPerformer.execute(synchronizationRequestUrl);
			}
			else{
				SynchronizationHistory historyRecord = new SynchronizationHistory();
				historyRecord.setStatus(SynchronizationHistory.NO_CONNECTION_STATUS);
				historyRecord.setSynchronizationTime(new Date().getTime());
				database.getRuntimeExceptionDao(SynchronizationHistory.class).create(historyRecord);
			}
		}
	}

	private String prepareSynchronizationRequestUrl(Long lastSynchronizationTime) {
		StringBuilder builder = new StringBuilder();
		builder.append(BackOfficeConstants.BO_URL);
		builder.append(BackOfficeConstants.SYNCHRONIZATION_SERVICE);
		if(lastSynchronizationTime != null){
			builder.append("/").append(lastSynchronizationTime);
		}
		else{
			builder.append(BackOfficeConstants.INIT_SYNC_METHOD);
		}
		return builder.toString();
	}

	private Long getLastSuccessSynchronizationTime() {
		QueryBuilder<SynchronizationHistory, ?> qb = database.getRuntimeExceptionDao(SynchronizationHistory.class).queryBuilder();
		try {
			qb.where().eq(SynchronizationHistory.STATUS_FIELD, SynchronizationHistory.SUCCESS_STATUS);
			List<SynchronizationHistory> syncHistory = qb.orderBy(SynchronizationHistory.SYNCHRONIZATION_TIME_FIELD, false).limit(1L).query();
			if(syncHistory.isEmpty()){
				return null;
			}
			return syncHistory.get(0).getSynchronizationTime();
		} catch (SQLException e) {
			throw new IllegalStateException("Can't get last success synchronization time." , e);
		}
	}

	private boolean isSynchronizationRequired() {
		QueryBuilder<SynchronizationHistory, ?> qb = database.getRuntimeExceptionDao(SynchronizationHistory.class).queryBuilder();
		try {
			List<SynchronizationHistory> syncHistory = qb.orderBy(SynchronizationHistory.SYNCHRONIZATION_TIME_FIELD, false).limit(1L).query();
			
			if(syncHistory == null || syncHistory.isEmpty() || !syncHistory.get(0).getStatus().equals(SynchronizationHistory.SUCCESS_STATUS)){
				return true;
			}
			else{
				 Long period = new Date().getTime() - syncHistory.get(0).getSynchronizationTime();
				 if(period > TWENTY_FOUR_HOURS){
					 return true;
				 }
				 else{
					 return false;
				 }
			}
			
		} catch (SQLException e) {
			Log.e(this.getClass().getName(), "Can't get synchronization history from database.", e);
			return true;
		}
	}
	
}
