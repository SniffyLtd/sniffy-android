package com.brand.sniffy.android.service;

import java.util.HashMap;
import java.util.Map;

import com.brand.sniffy.android.activity.LoginActivity;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

    private SharedPreferences pref;
     
    private Editor editor;
     
    private Context _context;
     
    private int PRIVATE_MODE = 0;
     
    private static final String PREF_NAME = "SniffyPreferences";
     
    private static final String IS_LOGIN = "IsLoggedIn";
     
    public static final String KEY_LOGIN = "login";
     
    public static final String KEY_PASSWORD = "password";
    
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void createLoginSession(String login, String password){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_LOGIN, login);
        editor.putString(KEY_PASSWORD, password);
         
        editor.commit();
    }   
    
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }     
    }
    
    /**
     * Get stored session data
     * */
    public Map<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_LOGIN, pref.getString(KEY_LOGIN, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
         
        return user;
    }
    
    /**
     * Clear session details
     * */
    public void logoutUser(){
        editor.clear();
        editor.commit();
         
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
    
    /**
     * Quick check for login
     * **/
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

	public Account getCurrentUser() {
		checkLogin();
		Map<String, String> userDetails = getUserDetails();
		Account account = new Account(userDetails.get(KEY_LOGIN), AuthenticationService.SNIFFY_USER_TYPE);
		return account;
	}
}
