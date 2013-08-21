package com.brand.sniffy.android.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Country  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7354098552443602343L;

	public static final String ID_FIELD = "id";
	
	private static final String NAME_FIELD = "name";

	private static final String CODE_FIELD = "code";

	@DatabaseField(id = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField(canBeNull = false)
	private String code;
	
	public Country(){
		
	}

	public Country(JSONObject  json) throws JSONException {
		id = json.getInt(ID_FIELD);
		name = json.getString(NAME_FIELD);
		code = json.getString(CODE_FIELD);
	}

	public Country(int id) {
		this.id = id;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
