package com.brand.sniffy.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Component {

	@DatabaseField(id = true)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEquivalentNames() {
		return equivalentNames;
	}

	public void setEquivalentNames(String equivalentNames) {
		this.equivalentNames = equivalentNames;
	}

	public ComponentRating getRating() {
		return rating;
	}

	public void setRating(ComponentRating rating) {
		this.rating = rating;
	}

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField
	private String equivalentNames;
	
	@DatabaseField(foreign = true, foreignAutoCreate = false)
	private ComponentRating rating;
}
