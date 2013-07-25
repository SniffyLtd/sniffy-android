package com.brand.applicationname.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Component {

	@DatabaseField(id = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField
	private String equivalentNames;
	
	@DatabaseField(foreign = true, foreignAutoCreate = false)
	private ComponentRating rating;
}
