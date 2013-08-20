package com.brand.sniffy.android;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.API.MainActivityNavigation;
import com.brand.sniffy.android.adapter.HistoryAdapter;
import com.brand.sniffy.android.adapter.HistoryAdapter.OnAwsomContexMenuListener;
import com.brand.sniffy.android.model.Scanning;
import com.brand.sniffy.android.service.ScanningService;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class HistoryFragment extends ListFragment implements OnAwsomContexMenuListener{
	
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		historyAdapter = new HistoryAdapter(getActivity(), R.layout.item_history, getScanningService().lastTeen());
        historyAdapter.setOnAwsomContexMenuListener(this);
        setListAdapter(historyAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Scanning selected = historyAdapter.getItem(position);
		historyAdapter.setSelected(selected.getId());
	}
	
	public void refresh() {
		historyAdapter.clear();
		historyAdapter.addAll(getScanningService().lastTeen());
	}

	@Override
	public void onRemove(int id) {
		getScanningService().remove(id);
		historyAdapter.setSelected(0);
		refresh();
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
