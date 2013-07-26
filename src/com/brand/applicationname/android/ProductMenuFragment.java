package com.brand.applicationname.android;

import com.brand.applicationname.android.adapter.ProductMenuAdapter;
import com.brand.applicationname.android.command.ProductCommandFactory;

import android.app.ListFragment;
import android.os.Bundle;

public class ProductMenuFragment extends ListFragment  {
	
	
	private ProductCommandFactory commandFactory;
	
	private ProductMenuAdapter menuAdapter;
	
	private ProductCommandFactory getCommandFactory(){
		if(commandFactory == null){
			this.commandFactory = new ProductCommandFactory(getActivity());
		}
		return commandFactory;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.menuAdapter = new ProductMenuAdapter(getActivity(), R.layout.item_product_menu, getCommandFactory().createComands());
		setListAdapter(menuAdapter);
	}
}
