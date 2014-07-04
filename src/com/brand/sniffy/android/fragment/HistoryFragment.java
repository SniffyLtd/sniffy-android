package com.brand.sniffy.android.fragment;

import com.brand.sniffy.android.model.Scanning;

public interface HistoryFragment {

	public interface OnOpenItemListener{
		public void onOpenItem(Scanning item, HistoryFragment historyFragment);
	}
	
	public interface OnShareItemListener{
		public void onShareItem(Scanning item, HistoryFragment historyFragment);
	}
	
	public interface OnRefreshItemListener{
		public void onRefreshItem(Scanning item, HistoryFragment historyFragment);
	}
	
	public void addOnOpenItemListener(OnOpenItemListener onOpenItemListener);
	
	public void removeOnOpenItemListener(OnOpenItemListener onOpenItemListener);
	
	public void addOnShareItemListener(OnShareItemListener onShareItemListener);
	
	public void removeOnShareItemListener(OnShareItemListener onShareItemListener);
	
	public void addOnRefreshItemListener(OnRefreshItemListener onRefreshItemListener);
	
	public void removeOnRefreshItemListener(OnRefreshItemListener onRefreshItemListener);
	
	public void refreshHistoryFragment();
}
