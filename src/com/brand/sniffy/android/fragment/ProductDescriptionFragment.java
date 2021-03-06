package com.brand.sniffy.android.fragment;
import com.brand.sniffy.android.R;
import com.brand.sniffy.android.activity.ProductDetailsActivity;
import com.brand.sniffy.android.model.Product;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductDescriptionFragment extends Fragment {

	private TextView descriptionView;
	
	private View rootView;
	
	private Product product;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_description, container, false);
        
        descriptionView = (TextView)rootView.findViewById(R.id.description_label);
        
    	
    	if(product != null && descriptionView != null){
    		descriptionView.setText(product.getDescription());
    	}
        return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		//Bundle extras = activity.getIntent().getExtras();
        //if (extras.containsKey(ProductDetailsActivity.PRODUCT_PARAMETER)) {
//        	product =  (Product)extras.get(ProductDetailsActivity.PRODUCT_PARAMETER);
  //      }
	}
}
