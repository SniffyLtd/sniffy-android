package com.brand.sniffy.android.fragment;

import com.brand.sniffy.android.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUserFragment extends Fragment {
	
	private EditText loginEditText;
	
	private EditText passwordEditText;
	
	private EditText passwordConfirmationEditText;
	
	private OnRegisterListener onRegisterListener;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_user, container, false);
        return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		loginEditText = (EditText) getActivity().findViewById(R.id.txtUsernameRegisterFrgm);
		passwordEditText = (EditText) getActivity().findViewById(R.id.txtPasswordRegisterFrgm);
		passwordConfirmationEditText = (EditText) getActivity().findViewById(R.id.txtPasswordConfirmationRegisterFrgm);
		
		Button loginButton = (Button) getActivity().findViewById(R.id.btnRegisterRegisterFrgm);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				performOnRegister();
			}
		});
	}

	private void performOnRegister() {
		String login = loginEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		String passwordConfirmation = passwordConfirmationEditText.getText().toString();
		
		if(login == null || login.isEmpty()){
			loginEditText.setError("Nazwa uýytkownika jest wymagana."); 
			return;
		}
		if(password == null || password.isEmpty()){
			passwordEditText.setError("Has¸o jest wymagane.");  
			return;
		}

		if(passwordConfirmation == null || passwordConfirmation.isEmpty() || !passwordConfirmation.equals(password)){
			passwordConfirmationEditText.setError("Has¸a nie sˆ identyczne."); // TODO 
			return;
		}
		
		OnRegisterListener listener = this.onRegisterListener;
		if(listener != null){
			listener.onRegister(login, password);
		}
	}
	
	public void setOnRegisterListener(OnRegisterListener onLoginListener){
		this.onRegisterListener = onLoginListener;
	}

	public interface OnRegisterListener{
		void onRegister(String login, String password);
	}

	public void userAlreadyExists() {
		loginEditText.setError("Nazwa uýytkownika jest zaj«ta."); // TODO
	}
}
