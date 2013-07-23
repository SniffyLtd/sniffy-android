package com.brand.applicationname.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Component {

	@DatabaseField(canBeNull = false)
	private int id;

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField
	private String equivalentNames;
	
	@DatabaseField(foreign = true)
	private ComponentRating rating;
}
