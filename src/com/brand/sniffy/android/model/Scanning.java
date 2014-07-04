package com.brand.sniffy.android.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class Scanning {

	public static final String ID_FIELD = "id";
	
	public static final String BARECODE_FIELD = "barecode";
	
	public static final String DATE_FIELD = "date";

	public static final String FOUND_PRODUCT_FIELD = "foundProduct";
	
	public static final String STATUS_FIELD = "status";
	
	public static final String STATUS_FOUND = "found";
	
	public static final String STATUS_NOT_FOUND = "notFound";
	
	public static final String STATUS_REJECTED = "rejected";

	public static final String STATUS_FAILED = "failed";

	public static final String STATUS_PENDING = "pending";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String barecode;
	
	@DatabaseField(canBeNull = false)
	private Date date;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Product foundProduct;

	@DatabaseField(canBeNull = false)
	private String deviceUUID;

	@DatabaseField(canBeNull = false)	
	private String deviceClass;

	@DatabaseField(canBeNull = false)
	private String status;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBarecode() {
		return barecode;
	}

	public void setBarecode(String barecode) {
		this.barecode = barecode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Product getFoundProduct() {
		return foundProduct;
	}

	public void setFoundProduct(Product foundProduct) {
		this.foundProduct = foundProduct;
	}

	public String getDeviceUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public String getDeviceClass() {
		return deviceClass;
	}

	public void setDeviceClass(String debiceClass) {
		this.deviceClass = debiceClass;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
