package com.brand.sniffy.android.fragment;

import com.brand.sniffy.android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return rootView;
    }
}
