package com.brand.applicationname.android.service;

import java.util.Random;

import android.content.Context;

import com.brand.applicationname.android.model.Database;
import com.brand.applicationname.android.model.Product;

public class LocalProductService {
	
	private Random random = new Random();
	
	private Database database;
	
	public LocalProductService(Context context){
		database = new Database(context);
	}

	public Product findProduct(String barcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cache(Product product) {
		// TODO Auto-generated method stub
		
	}

	public Product createProduct(String barecode) {
		Product product = new Product();
		product.setBarecode(barecode);
		product.setId(random.nextInt());
		product.setLocal(true);
		database.getRuntimeExceptionDao(Product.class).create(product);
		return product;
	}

}
