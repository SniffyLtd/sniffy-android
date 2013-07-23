package com.brand.applicationname.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ComponentRating {

	@DatabaseField(canBeNull = false)
	private int id;
	
    /**
     */
	@DatabaseField
    private String title;

    /**
     */
	@DatabaseField
    private String description;

    /**
     */
	@DatabaseField
    private String color;
	
}
