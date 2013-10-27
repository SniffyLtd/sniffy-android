package com.brand.sniffy.android.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Category {

	public static final String ID_FIELD = "id";
	
	private static final String NAME_FIELD = "name";

	@DatabaseField(id = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String name;

	public Category(JSONObject json) throws JSONException {
		id = json.getInt(ID_FIELD);
		name = json.getString(NAME_FIELD); 
	}
	
	public Category(){
		
	}

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


}
