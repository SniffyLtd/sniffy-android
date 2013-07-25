package com.brand.applicationname.android.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class Scanning {

	public static final String ID_FIELD = "id";
	
	public static final String BARECODE_FIELD = "barecode";
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBarecode() {
		return barecode;
	}

	public void setBarecode(String barecode) {
		this.barecode = barecode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Product getFoundProduct() {
		return foundProduct;
	}

	public void setFoundProduct(Product foundProduct) {
		this.foundProduct = foundProduct;
	}

	public static String getBarecodeField() {
		return BARECODE_FIELD;
	}

	public static final String DATE_FIELD = "date";

	public static final String FOUND_PRODUCT_FIELD = "foundProduct";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String barecode;
	
	@DatabaseField(canBeNull = false)
	private Date date;
	
	@DatabaseField(foreign = true )
	private Product foundProduct;
	
}
