package com.brand.sniffy.android.sync;

public class SynchronizationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public enum Reason {
		databaseError, connectionError, invalidDataError, authorizationError
	}
	
	private Reason reason;
	
	private SynchronizationException(String message, Throwable e, Reason reason) {
		super(message, e);
		this.reason = reason;
	}
	
	private SynchronizationException(String message, Reason reason) {
		super(message);
		this.reason = reason;
	}

	public static SynchronizationException databaseError(String message,  Throwable e){
		return new SynchronizationException(message,e, Reason.databaseError);
	}
	
	public static SynchronizationException connectionError(String message, Throwable e){
		return new SynchronizationException(message,e, Reason.connectionError);
	}
	
	public static SynchronizationException invalidDataError(String message, Throwable e){
		return new SynchronizationException(message,e, Reason.invalidDataError);
	}

	public static SynchronizationException authrorizationError(String message) {
		return new SynchronizationException(message, Reason.authorizationError);
	}
	
	public Reason getReason(){
		return reason;
	}
}
