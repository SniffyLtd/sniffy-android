package com.brand.applicationname.android;

import com.brand.applicationname.android.API.MainActivityNavigation;
import com.brand.applicationname.android.adapter.HistoryAdapter;
import com.brand.applicationname.android.adapter.HistoryAdapter.OnAwsomContexMenuListener;
import com.brand.applicationname.android.model.Scanning;
import com.brand.applicationname.android.service.ScanningService;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HistoryFragment extends Fragment implements OnAwsomContexMenuListener{
	

	private ListView historyList;
	
	private ScanningService scanningService;
	
	private HistoryAdapter historyAdapter;

	private MainActivityNavigation activityNavigation;
	
	private ScanningService getScanningService(){
		if(scanningService == null){
			scanningService = new ScanningService(this.getActivity());
		}
		return scanningService;
	}	
	
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
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        historyList = (ListView)rootView.findViewById(R.id.history_list);
        
        historyAdapter = new HistoryAdapter(getActivity(), R.layout.item_history, getScanningService().lastTeen());
        historyAdapter.setOnAwsomContexMenuListener(this);
        historyList.setAdapter(historyAdapter);
        
        historyList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int selectedPosition, long arg3) {
				Scanning selected = historyAdapter.getItem(selectedPosition);
				historyAdapter.setSelected(selected.getId());
			}
		});
        
        return rootView;
    }

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemove(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen(int id) {
		Scanning scanning = getScanningService().get(id);
		if(scanning != null){
			activityNavigation.openProductDetails(scanning.getFoundProduct());
		}
	}

	@Override
	public void onShare(int id) {
		// TODO Auto-generated method stub
		
	}
	
}
