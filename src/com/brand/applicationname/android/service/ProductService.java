package com.brand.applicationname.android.service;

import android.util.Log;

import com.brand.applicationname.android.model.Product;

public class ProductService {

	private LocalProductService localProductFinder;
	
	private RemoteProductService remoteProductFinder;
	
	public Product findProduct(String barcode) {
		
		Product product = getLocalProductFinder().findProduct(barcode);
		if(product == null){
			Log.i(this.getClass().getName(), "Product not present in local storage. Trying to search online.");
			product = getRemoteProductFinder().findProduct(barcode);
			getLocalProductFinder().cache(product);
		}
		return product;
	}

	public LocalProductService getLocalProductFinder() {
		if(localProductFinder == null){
			localProductFinder = new LocalProductService();
		}
		
		return localProductFinder;
	}

	public RemoteProductService getRemoteProductFinder() {
		if(remoteProductFinder == null){
			remoteProductFinder = new RemoteProductService();
		}
		
		return remoteProductFinder;
	}

}
