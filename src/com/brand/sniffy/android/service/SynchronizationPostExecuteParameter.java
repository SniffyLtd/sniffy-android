package com.brand.sniffy.android.service;

class SynchronizationPostExecuteParameter {

	SynchronizationPostExecuteParameter(SynchronizationStatus status){
		this.status = status;
	}
	
	SynchronizationPostExecuteParameter(SynchronizationStatus status, Long synchronizationTime){
		this.status = status;
		this.synchronizationTime = synchronizationTime;
	}
	
	 private SynchronizationStatus status;
	 
	 private Long synchronizationTime;

	public SynchronizationStatus getStatus() {
		return status;
	}

	public Long getSynchronizationTime() {
		return synchronizationTime;
	}

}
