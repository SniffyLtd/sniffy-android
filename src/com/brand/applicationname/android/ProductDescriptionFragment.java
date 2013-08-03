package com.brand.applicationname.android;

import com.brand.applicationname.android.model.Product;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductDescriptionFragment extends Fragment {

	private TextView descriptionView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_description, container, false);
        
        descriptionView = (TextView)rootView.findViewById(R.id.description_label);
        return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		Bundle extras = activity.getIntent().getExtras();
        if (extras.containsKey(ProductDetailsActivity.PRODUCT_PARAMETER)) {
        	Product product =  (Product)extras.get(ProductDetailsActivity.PRODUCT_PARAMETER);
        	
        	if(product != null && descriptionView != null){
        		descriptionView.setText(product.getDescription());
        	}
        }
	}
}
