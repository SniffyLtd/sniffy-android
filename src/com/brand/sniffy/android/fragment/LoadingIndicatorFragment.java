package com.brand.sniffy.android.fragment;

import com.brand.sniffy.android.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoadingIndicatorFragment extends Fragment {
	
	public void setMessage(String message){
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loading_indicator, container, false);
        return rootView;
    }
}
