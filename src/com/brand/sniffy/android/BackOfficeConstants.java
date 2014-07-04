package com.brand.sniffy.android;

public final class BackOfficeConstants {

	private BackOfficeConstants(){
		
	}
	
	public static final String BO_URL = "http://192.168.0.14:8080/sniffy-bo-web";//"http://sniffy-bo.herokuapp.com";
	
	public static final String SYNCHRONIZATION_SERVICE = "/mobile/sync";

	public static final String INIT_SYNC_METHOD = "/init";
	
	public static final String SEARCH_SERVICE = "/mobile/search";
	
	public static final String USER_SERVICE = "/mobile/user";
	
	public static final String AUTHENTICATION_METHOD = "/authenticate";

	public static final String REGISTER_DEVICE_METHOD = "/device/register";

	public static final String REGISTER_USER_METHOD = "/register";
	
}
