package com.brand.sniffy.android;

import java.util.List;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.adapter.ComponentsListAdapter;
import com.brand.sniffy.android.model.Component;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.service.ComponentsService;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class ComponentsFragment extends ListFragment{

	private ComponentsListAdapter listAdapter;
	
	private ComponentsService componentsService = new ComponentsService();
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		Bundle extras = activity.getIntent().getExtras();
        if (extras.containsKey(ProductDetailsActivity.PRODUCT_PARAMETER)) {
        	Product product =  (Product)extras.get(ProductDetailsActivity.PRODUCT_PARAMETER);
        	
        	List<Component> components = componentsService.getProductComponents(product);
        	
        	listAdapter = new ComponentsListAdapter(activity, R.layout.item_component, components);
        	setListAdapter(listAdapter);
        }
	}
}
