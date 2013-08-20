package com.brand.sniffy.android;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.adapter.ProductMenuAdapter;
import com.brand.sniffy.android.command.Command;
import com.brand.sniffy.android.command.ProductCommandFactory;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ProductMenuFragment extends ListFragment  implements OnItemClickListener{
	
	
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
		
		getListView().setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Command command = menuAdapter.getItem(arg2);
		command.execute(getActivity());
	}
	
}
