package com.brand.sniffy.android.activity;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.service.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class SplashScreenActivity extends Activity {
	
	private SessionManager sessionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		sessionManager = new SessionManager(this);
		
		sessionManager.checkLogin();
		
		if(sessionManager.isLoggedIn()){
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			this.finish();
		}
		
	}

}
