package com.brand.sniffy.android.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.brand.sniffy.android.model.Component;
import com.brand.sniffy.android.model.Database;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.model.ProductComponents;
import com.j256.ormlite.stmt.QueryBuilder;

class LocalProductService {
	
	private Database database;
	
	private CategoryService categoryService;
	
	private ProducerService producerService;
	
	LocalProductService(Context context, Account account){
		database = new Database(context, account);
		categoryService = new CategoryService(context, account);
		producerService = new ProducerService(context, account);
	}

	Product findProduct(String barcode) {
		QueryBuilder<Product, ?> qb = database.getRuntimeExceptionDao(Product.class).queryBuilder();
		try {
			qb.where().eq(Product.BARECODE_FIELD, barcode);
			return  qb.queryForFirst();
		} catch (SQLException e) {
			Log.e(this.getClass().getName(), "Can't get product from database.", e);
		}
		return null;
	}
	
	Product get(int id) {
		try{
			List<Product> result  = database.getRuntimeExceptionDao(Product.class).queryBuilder().where().eq(Product.ID_FIELD, id).query();
			if(result.size() > 0){
				return result.get(0);
			}
			else{
				return null;
			}
		}catch(SQLException e){
			throw new IllegalStateException(e);
		}
	}

	void create(Product product) {
		try {
			resolveReferences(product);
			database.getRuntimeExceptionDao(Product.class).create(product);
			updateProductComponents(product, product.getComponents());
			Log.d(this.getClass().getName(), String.format("Product %d created.", product.getId()));
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	
	void update(Product product) {
		try {
			resolveReferences(product);
			database.getRuntimeExceptionDao(Product.class).update(product);
			updateProductComponents(product, product.getComponents());
			Log.d(this.getClass().getName(), String.format("Product %d updated.", product.getId()));
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	

	private void updateProductComponents(Product product, Set<Component> components) throws SQLException {
		 database.getRuntimeExceptionDao(ProductComponents.class).deleteBuilder()
				.where().eq(ProductComponents.PRODUCT_FIELD, product).query();
		
		for(Component component : components){
			ProductComponents productComponent = new ProductComponents();
			productComponent.setComponent(component);
			productComponent.setProduct(product);
			database.getRuntimeExceptionDao(ProductComponents.class).create(productComponent);
		}	
	}

	private void resolveReferences(Product product) {
		if(product.getCategory() != null){
			categoryService.save(product.getCategory());
		}
		if(product.getProducer() != null){
			producerService.save(product.getProducer());
		}
	}

	public boolean hasComponents(Product product) {
		try {
			List<ProductComponents> components = database.getRuntimeExceptionDao(ProductComponents.class).queryBuilder()
			.where().eq(ProductComponents.PRODUCT_FIELD, product).query();
			if(components != null && components.size() > 0){
				return true;
			}

			return false;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
