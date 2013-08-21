package com.brand.sniffy.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONObject;
import org.json.JSONException;

@DatabaseTable
public class Component {
	
	private static final String COMPONENT_RATING_ID_FIELD = "rating_id";

	private static final String EQUIVALENT_NAMES_FIELD = "equivalentNames";

	private static final String NAME_FIELD = "name";

	public static final String ID_FIELD = "id";

	public Component(){
		
	}
	
	public Component(JSONObject json) throws JSONException{
		id = json.getInt(ID_FIELD);
		name = json.getString(NAME_FIELD);
		equivalentNames = json.getString(EQUIVALENT_NAMES_FIELD);
		rating = new ComponentRating(json.getInt(COMPONENT_RATING_ID_FIELD));  
	}
	

	public Component(int id) {
		this.id = id;
	}


	@DatabaseField(id = true)
	private int id;
	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField
	private String equivalentNames;
	
	@DatabaseField(foreign = true, foreignAutoCreate = false, foreignAutoRefresh = true)
	private ComponentRating rating;
	
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
}
