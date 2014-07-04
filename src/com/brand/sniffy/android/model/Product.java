package com.brand.sniffy.android.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static final String BARECODE_FIELD = "barcode";
	
	public static final String DESCRIPTION_FIELD = "description";

	private static final String PRODUCER_FIELD = "producer";
	
	private static final String COMPONENTS_JSON_FIELD = "components";

	private static final String CATEGORY_FIELD = "category";

	@DatabaseField(id = true, canBeNull = false)
	private int id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField(canBeNull = false)
	private String barcode;
	
	@DatabaseField
	private String description;

	@DatabaseField
	private  boolean local;

	@DatabaseField(foreign = true, foreignAutoCreate = false, foreignAutoRefresh = true)
	private Producer producer;
	
	@DatabaseField(foreign = true, foreignAutoCreate = false, foreignAutoRefresh = true)
	private Category category;
	
	private transient Set<Component> components = new HashSet<Component>();
	
	public Product(){
		
	}
	
	public Product(JSONObject json) throws JSONException  {
		id = json.getInt(ID_FIELD);
		name = json.getString(NAME_FIELD);
		barcode = json.getString(BARECODE_FIELD);
		if(json.has(DESCRIPTION_FIELD)){
			description = json.getString(DESCRIPTION_FIELD);
		}
		if(json.has(CATEGORY_FIELD)){
			category = new Category(json.getJSONObject(CATEGORY_FIELD));
		}
		if(json.has(PRODUCER_FIELD)){
			producer = new Producer(json.getJSONObject(PRODUCER_FIELD));
		}
		if(json.has(COMPONENTS_JSON_FIELD)){
			JSONArray componetIds = json.getJSONArray(COMPONENTS_JSON_FIELD);
			for(int i=0; i < componetIds.length(); ++i){
				components.add(new Component(componetIds.getInt(i)));
			}
		}
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Set<Component> getComponents() {
		return components;
	}

	public void setComponents(Set<Component> components) {
		this.components = components;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
