package com.brand.sniffy.android.fragment;

import java.util.ArrayList;
import java.util.List;

import com.brand.sniffy.android.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {
	
	private EditText loginEditText;
	
	private EditText passwordEditText;
	
	private List<OnLoginListener> onLoginListeners = new ArrayList<LoginFragment.OnLoginListener>();
	
	private String emptyLoginErrorMessage;
	
	private String emptyPasswordErrorMessage;
	
	private String wrongLoginOrPasswordErrorMessage;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		loginEditText = (EditText) getActivity().findViewById(R.id.txtUsernameLoginFrgm);
		passwordEditText = (EditText) getActivity().findViewById(R.id.txtPasswordLoginFrgm);
		
		wrongLoginOrPasswordErrorMessage = getActivity().getString(R.string.wrong_login_or_password_error);
		emptyLoginErrorMessage = getActivity().getString(R.string.empty_login_error);
		emptyPasswordErrorMessage = getActivity().getString(R.string.empty_password_error);
		
		Button loginButton = (Button) getActivity().findViewById(R.id.btnLoginLoginFrgm);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				performOnLogin();
			}
		});
		
		Button registerButton = (Button) getActivity().findViewById(R.id.btnRegisterLoginFrgm);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				performRegister();
			}
		});
	}

	private void performOnLogin() {
		String login = getLogin();
		String password = getPassword();
		
		if(login == null || login.isEmpty()){
			loginEditText.setError(emptyLoginErrorMessage);
			return;
		}
		if(password == null || password.isEmpty()){
			passwordEditText.setError(emptyPasswordErrorMessage); 
			return;
		}
		
		for(OnLoginListener listener : this.onLoginListeners){
			if(listener != null){
				listener.onLogin(this);
			}
		}
	}

	private void performRegister(){
		for(OnLoginListener listener : this.onLoginListeners){
			if(listener != null){
				listener.onPerformRegister(this);
			}
		}
	}
	
	public void addOnLoginListener(OnLoginListener onLoginListener){
		this.onLoginListeners.add(onLoginListener);
	}
	
	public void removeOnLoginListener(OnLoginListener onLoginListener){
		int index = this.onLoginListeners.indexOf(onLoginListener);
		if(index>0){
			this.onLoginListeners.remove(index);
		}
	}

	public interface OnLoginListener{
		
		void onLogin(final LoginFragment loginFragment);

		void onPerformRegister(final LoginFragment loginFragment);
	}

	public void displayWrongLoginOrPassword() {
		passwordEditText.setError(wrongLoginOrPasswordErrorMessage);
	}
	
	public String getLogin(){
		return loginEditText.getText().toString();
	}
	
	public String getPassword(){
		return passwordEditText.getText().toString();
	}
}
