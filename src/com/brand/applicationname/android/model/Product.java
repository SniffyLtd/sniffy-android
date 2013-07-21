package com.brand.applicationname.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Product {

	@DatabaseField
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = false)
	private String barecode;
	
	@DatabaseField
	private String description;
	
}
