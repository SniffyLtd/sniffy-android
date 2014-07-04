package com.brand.sniffy.android;

import com.brand.sniffy.android.service.SynchronizationService;
import com.brand.sniffy.android.sync.SynchronizationException;
import com.brand.sniffy.android.sync.SynchronizationException.Reason;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

public class SyncAdapter extends AbstractThreadedSyncAdapter  {

	private SynchronizationService synchronizationService;
	
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		Log.d(SyncAdapter.class.getName(), "New sync adapter created.");
		synchronizationService = new SynchronizationService(context);
	}

	@Override
	public void onPerformSync(Account account, 
			Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {
		Log.d(SyncAdapter.class.getName(), "Sync performed.");
		try{
			synchronizationService.requestSynchronization(account, syncResult.stats);
		}
		catch(SynchronizationException e){
			Log.e(SyncAdapter.class.getName(), "Synchronization failed, reason: " + e.getReason().name(), e);
			if(Reason.databaseError.equals(e.getReason())){
				syncResult.databaseError = true;
			}
			else if(Reason.connectionError.equals(e.getReason())){
				syncResult.stats.numIoExceptions++;
			}
			else if(Reason.invalidDataError.equals(e.getReason())){
				syncResult.stats.numParseExceptions++;
			}
			else if(Reason.authorizationError.equals(e.getReason())){
				syncResult.stats.numAuthExceptions++;
			}
		}
	}
}
