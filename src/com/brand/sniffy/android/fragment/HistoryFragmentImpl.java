package com.brand.sniffy.android.fragment;

import java.util.ArrayList;
import java.util.List;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.adapter.HistoryAdapter;
import com.brand.sniffy.android.adapter.HistoryAdapter.OnAwsomContexMenuListener;
import com.brand.sniffy.android.model.Scanning;
import com.brand.sniffy.android.service.ScanningService;
import com.brand.sniffy.android.service.SessionManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class HistoryFragmentImpl extends ListFragment implements HistoryFragment, OnAwsomContexMenuListener{
	
	private ScanningService scanningService;
	
	private HistoryAdapter historyAdapter;
	
	private SessionManager sessionManager;
	
	private List<OnRefreshItemListener> onRefreshItemListeners = new ArrayList<HistoryFragment.OnRefreshItemListener>();
	
	private List<OnOpenItemListener> onOpenItemListeners = new ArrayList<HistoryFragment.OnOpenItemListener>();
	
	private List<OnShareItemListener> onShareItemListeners = new ArrayList<HistoryFragment.OnShareItemListener>();
	
	private ScanningService getScanningService(){
		if(scanningService == null){
			scanningService = new ScanningService(this.getActivity(),getSessionManager().getCurrentUser());
		}
		return scanningService;
	}
	
	private SessionManager getSessionManager(){
		if(sessionManager == null){
			sessionManager = new SessionManager(this.getActivity());
		}
		return sessionManager;
	}
	
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
	
	@Override
	public void onRemove(int id) {
		getScanningService().remove(id);
		historyAdapter.setSelected(0);
		refreshHistoryFragment();
	}

	@Override
	public void onRefresh(int id) {
		Scanning scanning = getScanningService().get(id);
		if(scanning != null){
			for(OnOpenItemListener listener : onOpenItemListeners){
				listener.onOpenItem(scanning, this);
			}
		}
	}

	@Override
	public void onOpen(int id) {
		Scanning scanning = getScanningService().get(id);
		if(scanning != null){
			for(OnOpenItemListener listener : onOpenItemListeners){
				listener.onOpenItem(scanning, this);
			}
		}
	}

	@Override
	public void onShare(int id) {
		Scanning scanning = getScanningService().get(id);
		if(scanning != null){
			for(OnShareItemListener listener : onShareItemListeners){
				listener.onShareItem(scanning, this);
			}
		}
	}

	@Override
	public void addOnOpenItemListener(OnOpenItemListener onOpenItemListener) {
		onOpenItemListeners.add(onOpenItemListener);	
	}

	@Override
	public void removeOnOpenItemListener(OnOpenItemListener onOpenItemListener) {
		int index = onOpenItemListeners.indexOf(onOpenItemListener);
		if(index > -1){
			onOpenItemListeners.remove(index);
		}
	}

	@Override
	public void addOnShareItemListener(OnShareItemListener onShareItemListener) {
		onShareItemListeners.add(onShareItemListener);
	}

	@Override
	public void removeOnShareItemListener(
			OnShareItemListener onShareItemListener) {
		int index = onShareItemListeners.indexOf(onShareItemListener);
		if(index > -1){
			onShareItemListeners.remove(index);
		}
	}

	@Override
	public void addOnRefreshItemListener(
			OnRefreshItemListener onRefreshItemListener) {
		onRefreshItemListeners.add(onRefreshItemListener);
	}

	@Override
	public void removeOnRefreshItemListener(
			OnRefreshItemListener onRefreshItemListener) {
		int index = onRefreshItemListeners.indexOf(onRefreshItemListener);
		if(index > -1){
			onOpenItemListeners.remove(index);
		}
	}

	@Override
	public void refreshHistoryFragment() {
		historyAdapter.clear();
		historyAdapter.addAll(getScanningService().lastTeen());
	}
	
}
