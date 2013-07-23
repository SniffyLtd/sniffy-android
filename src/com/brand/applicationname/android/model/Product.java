package com.brand.applicationname.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Product {
	
	public static final String ID_FIELD = "id";
	
	public static final String NAME_FIELD = "name";
	
	public static final String BARECODE_FIELD = "barecode";
	
	public static final String DESCRIPTION_FIELD = "description";

	@DatabaseField(canBeNull = false)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = false)
	private String barecode;
	
	@DatabaseField
	private String description;
	
}
