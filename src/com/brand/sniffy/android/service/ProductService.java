package com.brand.sniffy.android.service;

import org.json.JSONArray;
import org.json.JSONException;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncStats;
import android.util.Log;

import com.brand.sniffy.android.model.Product;

public class ProductService {
	
	public ProductService(Context context, Account account){
		this.localProductService = new LocalProductService(context, account);
		this.remoteProductService = new RemoteProductService(context, account);
		this.scanningService = new ScanningService(context, account);
	}

	private LocalProductService localProductService;
	
	private RemoteProductService remoteProductService;
	
	private ScanningService scanningService;
	
	public Product findProduct(String barcode) {
		Product product = localProductService.findProduct(barcode);
		if(product == null || product.isLocal()){
			Log.i(this.getClass().getName(), "Product not present in local storage. Trying to search online.");
			product = remoteProductService.findProduct(barcode);
			save(product);
		}
		return product;
	}

	public void save(Product product){
		Product existingProduct = localProductService.get(product.getId());
		if(existingProduct == null){
			localProductService.create(product);
		}
		else{
			localProductService.update(product);
		}
		
		scanningService.updateResults(product);
	}

	public void applyChanges(JSONArray products, SyncStats stats) throws JSONException {
		for(int i =0 ; i< products.length(); ++i){
			Product product =  new Product(products.getJSONObject(i));
			Product existingProduct = localProductService.get(product.getId());
			if(existingProduct == null){
				localProductService.create(product);
				stats.numInserts++;
			}
			else{
				localProductService.update(product);
				stats.numUpdates++;
			}
		}
	}

	public Product get(int id) {
		return localProductService.get(id);
	}

	public boolean hasComponents(Product product) {
		return localProductService.hasComponents(product);
	}
}
