package com.brand.sniffy.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SynchronizationHistory {

	public static final String SUCCESS_STATUS = "success";

	public static final String NO_CONNECTION_STATUS = "noConnection";

	public static final String BO_INTERNAL_ERROR_STATUS = "boInternalError";

	public static final String OTHER_ERROR_STATUS = "otherError";

	public static final String SYNCHRONIZATION_TIME_FIELD = "synchronizationTime";

	public static final String STATUS_FIELD = "status";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSynchronizationTime() {
		return synchronizationTime;
	}

	public void setSynchronizationTime(Long synchronizationTime) {
		this.synchronizationTime = synchronizationTime;
	}

	@DatabaseField(canBeNull = false)
	private String status;
	
	@DatabaseField(canBeNull = false)
	private Long synchronizationTime;
	
}
