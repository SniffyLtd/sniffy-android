package com.brand.sniffy.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONObject;
import org.json.JSONException;

@DatabaseTable
public class ComponentRating {
	
	public static final String ID_FIELD = "id";

	private static final String TITLE_FIELD = "title";

	private static final String DESCRIPTION_FIELD = "description";

	private static final String COLOR_FIELD = "color";
	
	public ComponentRating(){
		
	}
	
	public ComponentRating(int id){
		this.id = id;
	}
	
	public ComponentRating(JSONObject json) throws JSONException{
		id = json.getInt(ID_FIELD);
		title = json.getString(TITLE_FIELD);
		description = json.getString(DESCRIPTION_FIELD);
		color = json.getString(COLOR_FIELD);
	}

	@DatabaseField(id = true)
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
	
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
