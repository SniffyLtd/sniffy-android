package com.brand.applicationname.android.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ID_FIELD = "id";
	
	public static final String NAME_FIELD = "name";
	
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

	public String getBarecode() {
		return barecode;
	}

	public void setBarecode(String barecode) {
		this.barecode = barecode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public static final String BARECODE_FIELD = "barecode";
	
	public static final String DESCRIPTION_FIELD = "description";

	@DatabaseField(id = true, canBeNull = false)
	private int id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField(canBeNull = false)
	private String barecode;
	
	@DatabaseField
	private String description;

	@DatabaseField
	private  boolean local;
	
}
