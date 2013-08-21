package com.brand.sniffy.android;

import com.brand.sniffy.android.sync.SynchronizationStatus;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SynchronizationStatusBroadcastReceiver extends BroadcastReceiver{

	private static final String STATUS = "status";
	
	private static final int mNotificationId = 768901344;

	@Override
	public void onReceive(Context context, Intent intent) {
		String strStatus = intent.getStringExtra(STATUS);

		SynchronizationStatus status = SynchronizationStatus.valueOf(strStatus);
		
		switch(status){
		case STARTED:
			showNotification(context);
			break;
		case APLAYING_CHANGES:
			break;
		case APLAYING_SYNC_DATA_ERROR:
		case FINISHED_WITH_ERROR:
		case FINISHED_WITH_SUCCESS:
			cancelNotification(context);
			break;
		}
	}
	
	private void showNotification(Context context) {
	    Object service = context.getSystemService(Context.NOTIFICATION_SERVICE);
	    NotificationManager notificationManager = (NotificationManager) service;
	    int icon = android.R.drawable.stat_notify_sync;
	    String tickerText = null;
	    long when = 0;
	    Notification notification = new Notification(icon, tickerText, when);
	    CharSequence contentTitle = context.getText(R.string.app_name);
	    CharSequence contentText = context.getText(R.string.components_synchronization_message);
	    notification.when = System.currentTimeMillis();
	    notification.flags |= Notification.FLAG_ONGOING_EVENT;
	    notification.setLatestEventInfo(context, contentTitle, contentText, null);
	    notificationManager.notify(mNotificationId, notification);
	}

	private void cancelNotification(Context context) {
	    Object service = context.getSystemService(Context.NOTIFICATION_SERVICE);
	    NotificationManager nm = (NotificationManager) service;
	    nm.cancel(mNotificationId);
	}

}
