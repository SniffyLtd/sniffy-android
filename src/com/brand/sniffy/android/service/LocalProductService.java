package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.Random;

import android.content.Context;
import android.util.Log;

import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Product;
import com.j256.ormlite.stmt.QueryBuilder;

public class LocalProductService {
	
	private Random random = new Random();
	
	private Database database;
	
	public LocalProductService(Context context){
		database = new Database(context);
	}

	public Product findProduct(String barcode) {
		QueryBuilder<Product, ?> qb = database.getRuntimeExceptionDao(Product.class).queryBuilder();
		try {
			qb.where().eq(Product.BARECODE_FIELD, barcode);
			return  qb.queryForFirst();
		} catch (SQLException e) {
			Log.e(this.getClass().getName(), "Can't get product from database.", e);
		}
		return null;
	}

	public void cache(Product product) {
	//	Product localProduct = findProduct(product.getBarecode());
	//	product.setId(localProduct.getId());
		
	}

	public Product createProduct(String barecode) {
		Product product = new Product();
		product.setBarecode(barecode);
		product.setId(random.nextInt(Integer.MAX_VALUE-100000)+100000);
		product.setLocal(true);
		database.getRuntimeExceptionDao(Product.class).create(product);
		return product;
	}

}
