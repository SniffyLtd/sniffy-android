package com.brand.sniffy.android.fragment;

public interface ScanerFragment {

	public interface OnSearchListener{
		public void onSearch(String bracode, ScanerFragment scanningFragment);
	}
	
	public void addOnSearchListner(OnSearchListener onSearchListener);
	
	public void removeOnSearchListener(OnSearchListener onSearchListener);
}
