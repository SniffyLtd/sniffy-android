package com.brand.sniffy.android;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountAuthenticatorService extends Service{

	 private static AccountAuthenticatorImpl accountAuthenticator = null;
	 
	 public AccountAuthenticatorService() {
	  super();
	 }
	
	@Override
	public IBinder onBind(Intent intent) {
		IBinder ret = null;
		if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT)){
		   ret = getAuthenticator().getIBinder();
		}
		return ret;
	}
	
	private AccountAuthenticatorImpl getAuthenticator() {
		  if (accountAuthenticator == null)
			  accountAuthenticator = new AccountAuthenticatorImpl(this);
		  return accountAuthenticator;
	}

}
