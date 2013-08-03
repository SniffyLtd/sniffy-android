package com.brand.applicationname.android.service;

import android.content.Context;
import android.util.Log;

import com.brand.applicationname.android.model.Product;

public class ProductService {
	
	private Context context;
	
	public ProductService(Context context){
		this.context = context;
	}

	private LocalProductService localProductFinder;
	
	private RemoteProductService remoteProductFinder;
	
	public Product findProduct(String barcode) {
		
		Product product = getLocalProductFinder().findProduct(barcode);
		if(product == null || product.isLocal()){
			Log.i(this.getClass().getName(), "Product not present in local storage. Trying to search online.");
			Product remoteProduct = getRemoteProductFinder().findProduct(barcode);
			if(remoteProduct != null){
				product = remoteProduct;
				getLocalProductFinder().cache(product);
			}
		}
		return product;
	}

	public LocalProductService getLocalProductFinder() {
		if(localProductFinder == null){
			localProductFinder = new LocalProductService(context);
		}
		
		return localProductFinder;
	}

	public RemoteProductService getRemoteProductFinder() {
		if(remoteProductFinder == null){
			remoteProductFinder = new RemoteProductService();
		}
		
		return remoteProductFinder;
	}

	public Product createLocalProduct(String baredoce) {
		return getLocalProductFinder().createProduct(baredoce);
	}

}
