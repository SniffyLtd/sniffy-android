package com.brand.sniffy.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ProductComponents {
	
	

	public static final String COMPONENT_FIELD = "component_id";

	public static final String PRODUCT_FIELD = "product_id";

	@DatabaseField(foreign = true, foreignAutoCreate = false, foreignAutoRefresh = true)
	private Product product;

	@DatabaseField(foreign = true, foreignAutoCreate = false, foreignAutoRefresh = true)
	private Component component;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}
	
	
}
