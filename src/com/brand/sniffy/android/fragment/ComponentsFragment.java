package com.brand.sniffy.android.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.brand.sniffy.android.R;
import com.brand.sniffy.android.activity.ProductDetailsActivity;
import com.brand.sniffy.android.adapter.ComponentsListAdapter;
import com.brand.sniffy.android.model.Component;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.service.ComponentsService;
import com.brand.sniffy.android.service.SessionManager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class ComponentsFragment extends ListFragment{

	private ComponentsListAdapter listAdapter;
	
	private ComponentsService componentsService;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		componentsService = new ComponentsService(activity, new SessionManager(activity).getCurrentUser());
		
		Bundle extras = activity.getIntent().getExtras();
        if (extras.containsKey(ProductDetailsActivity.PRODUCT_PARAMETER)) {
        	Product product =  (Product)extras.get(ProductDetailsActivity.PRODUCT_PARAMETER);
        	
        	List<Component> components = new ArrayList<Component>();
			try {
				components = componentsService.getProductComponents(product);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	listAdapter = new ComponentsListAdapter(activity, R.layout.item_component, components);
        	setListAdapter(listAdapter);
        }
	}
}
