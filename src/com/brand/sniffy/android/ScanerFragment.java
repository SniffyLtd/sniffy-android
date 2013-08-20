package com.brand.sniffy.android;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.API.MainActivityNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ScanerFragment extends Fragment {
	
	private MainActivityNavigation activityNavigation;
	
	public static final int SCAN_REQUEST_CODE = 0;

	protected static final String BARECODE_PARAM = "barecode";

	private Button scanButton;
	
	private Button searchButton;
	
	private EditText barecodeInput;
	
	@Override
	public void onAttach(android.app.Activity activity) {
		if(activity instanceof MainActivityNavigation){
			activityNavigation = (MainActivityNavigation)activity;
		}
		else{
			throw new IllegalArgumentException("activity is not instance of MainActivityNavigation interface.");
		}
		super.onAttach(activity);
	};
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scaner, container, false);
        
        scanButton = (Button)rootView.findViewById(R.id.scan_button);
        searchButton = (Button) rootView.findViewById(R.id.search_button);
        barecodeInput = (EditText)rootView.findViewById(R.id.barecode_input);
        
        scanButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		        intent.putExtra("SCAN_MODE", "BARE_CODE_MODE");
				getActivity().startActivityForResult(intent, SCAN_REQUEST_CODE);
			}
		});
        searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String barecode = barecodeInput.getText().toString();
				activityNavigation.search(barecode);
			}
		});
        return rootView;
    }

	public void clean() {
		barecodeInput.setText("");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
