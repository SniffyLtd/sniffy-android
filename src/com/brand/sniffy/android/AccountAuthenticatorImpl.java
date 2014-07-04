package com.brand.sniffy.android;

import com.brand.sniffy.android.activity.LoginActivity;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AccountAuthenticatorImpl extends AbstractAccountAuthenticator {

	private Context mContext;
	
	//private AuthenticationService authenticationService;
		 
	public AccountAuthenticatorImpl(Context context) {
		  super(context);
		  mContext = context;
	}
	
	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response,
				String arg1, String arg2, String[] arg3, Bundle arg4)
				throws NetworkErrorException {
		Bundle reply = new Bundle();
			   
		Intent i = new Intent(mContext, LoginActivity.class);
		i.setAction(LoginActivity.LOGIN_ACTION);
		i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		reply.putParcelable(AccountManager.KEY_INTENT, i);
		return reply;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse arg0, Account arg1, Bundle arg2) throws NetworkErrorException {
			// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse arg0,
				String arg1) {
		
		return null;
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse arg0,
			Account arg1, String arg2, Bundle arg3)
				throws NetworkErrorException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getAuthTokenLabel(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Bundle hasFeatures(AccountAuthenticatorResponse arg0,
				Account arg1, String[] arg2) throws NetworkErrorException {
			// TODO Auto-generated method stub
			return null;
		}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse reponse,
			Account account, String password, Bundle userData)
			throws NetworkErrorException {
			// TODO Auto-generated method stub
		return null;
	}
}
		
