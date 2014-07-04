package com.brand.sniffy.android.activity;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.fragment.LoadingIndicatorFragment;
import com.brand.sniffy.android.fragment.LoginFragment;
import com.brand.sniffy.android.fragment.RegisterUserFragment;
import com.brand.sniffy.android.fragment.LoginFragment.OnLoginListener;
import com.brand.sniffy.android.fragment.RegisterUserFragment.OnRegisterListener;
import com.brand.sniffy.android.service.AuthenticationService;
import com.brand.sniffy.android.service.SessionManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ViewFlipper;

public class LoginActivity extends FragmentActivity implements OnLoginListener , OnRegisterListener{

	public static final String LOGIN_ACTION = "com.sniffy.android.LOGIN";

	private SessionManager sessionManager;
	
	private AuthenticationService authenticationService;
	
	private ViewFlipper viewFlipper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.sessionManager = new SessionManager(this);
        this.authenticationService = new AuthenticationService(this);
        
        this.getLoginFragment().addOnLoginListener(this);
        this.getRegisterUserFragment().setOnRegisterListener(this);
        this.viewFlipper = (ViewFlipper)findViewById(R.id.login_activity_view_flipper);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
	private  LoginFragment getLoginFragment(){
        return (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.login_fragment);
    }
	
	private LoadingIndicatorFragment getLoadingIndicatorFragment(){
		return (LoadingIndicatorFragment) getSupportFragmentManager().findFragmentById(R.id.login_loading_indicator_fragment);
	}
	
	private RegisterUserFragment getRegisterUserFragment(){
		return (RegisterUserFragment) getSupportFragmentManager().findFragmentById(R.id.register_fragment);
	}

	@Override
	public void onLogin(final LoginFragment loginFragment) {
		String login = loginFragment.getLogin();
		String password = loginFragment.getPassword();
		getLoadingIndicatorFragment().setMessage("Loguj«...");
		this.viewFlipper.showNext();
		new AsyncTask<String, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(String... credentials) {
				
				 boolean result = authenticationService.authenticate(credentials[0], credentials[1]);
				 if(result){
					 sessionManager.createLoginSession(credentials[0], credentials[1]);
				 }
				 return result;
			}
			
			protected void onPostExecute(Boolean result) {
				if(result){
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}
				else{
					viewFlipper.showPrevious();
					loginFragment.displayWrongLoginOrPassword();
				}
			};
		}.execute(new String[]{ login, password});
	}


	@Override
	public void onPerformRegister(final LoginFragment loginFragment) {
		this.viewFlipper.showNext();
		this.viewFlipper.showNext();	
	}

	@Override
	public void onRegister(String login, String password) {
		getLoadingIndicatorFragment().setMessage("Rejestruje nowego uýytkownika...");
		this.viewFlipper.showPrevious();
		new AsyncTask<String, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(String... credentials) {
				
				 boolean result = authenticationService.register(credentials[0], credentials[1]);
				 if(result){
					 sessionManager.createLoginSession(credentials[0], credentials[1]);
				 }
				 return result;
			}
			
			protected void onPostExecute(Boolean result) {
				if(result){
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}
				else{
					viewFlipper.showNext();
					getRegisterUserFragment().userAlreadyExists();
				}
			};
		}.execute(new String[]{ login, password});
	}
}
