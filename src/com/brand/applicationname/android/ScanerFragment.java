package com.brand.applicationname.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ScanerFragment extends Fragment {
	
	public static final int SCAN_REQUEST_CODE = 0;

	protected static final int PRODUCT_REQUEST_CODE = 1;

	protected static final String BARECODE_PARAM = "barecode";

	private Button scanButton;
	
	private Button searchButton;
	
	private EditText barecodeInput;
	
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
				String barecode= barecodeInput.getText().toString();
				Intent intent = new Intent(ScanerFragment.this.getActivity(), ProductDetailsActivity.class);
				intent.putExtra(BARECODE_PARAM, barecode);
				getActivity().startActivityForResult(intent, PRODUCT_REQUEST_CODE);
			}
		});
        return rootView;
    }

	public void requestSearch(String barecode) {
		barecodeInput.setText(barecode);
		searchButton.performClick();
		
	}

	

}
