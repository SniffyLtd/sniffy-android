package com.brand.applicationname.android.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class Scaning {

	public static final String ID_FIELD = "id";
	
	public static final String BARECODE_FIELD = "barecode";
	
	public static final String DATE_FIELD = "date";

	public static final String FOUND_PRODUCT_FIELD = "foundProduct";
	
	@DatabaseField(allowGeneratedIdInsert = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String barecode;
	
	@DatabaseField(canBeNull = false)
	private Date date;
	
	@DatabaseField(foreign = true )
	private Product foundProduct;
	
}
