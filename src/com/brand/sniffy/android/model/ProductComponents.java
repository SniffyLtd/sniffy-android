package com.brand.sniffy.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ProductComponents {

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
