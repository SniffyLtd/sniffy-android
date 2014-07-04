package com.brand.sniffy.android.fragment;

import com.brand.sniffy.android.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProducerDetailsFragment extends Fragment {

	private View rootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_producer_details, container, false);
        
        return rootView;
	}
}
