package com.brand.sniffy.android.fragment;


import java.util.ArrayList;
import java.util.List;

import com.brand.sniffy.android.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ScanerFragmentImpl extends Fragment implements ScanerFragment {
	
	public static final int SCAN_REQUEST_CODE = 0;

	protected static final String BARECODE_PARAM = "barecode";

	private Button scanButton;
	
	private Button searchButton;
	
	private EditText barecodeInput;
	
	private List<OnSearchListener> onSearchListeners = new ArrayList<ScanerFragment.OnSearchListener>();
	
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
				for(OnSearchListener listener : onSearchListeners){
					listener.onSearch(barecode, ScanerFragmentImpl.this);
				}
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

	@Override
	public void addOnSearchListner(OnSearchListener onSearchListener) {
		onSearchListeners.add(onSearchListener);
	}

	@Override
	public void removeOnSearchListener(OnSearchListener onSearchListener) {
		int index = onSearchListeners.indexOf(onSearchListener);
		if(index > -1){
			onSearchListeners.remove(index);
		}
	}
}
