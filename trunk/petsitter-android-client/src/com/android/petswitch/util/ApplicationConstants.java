package com.android.petswitch.util;

public class ApplicationConstants {

	public static String USER_PREF = "USER_PREF";

	public static String APPLICATION_HOST = "http://10.0.2.2:8080";
	public static String PETSITTER_SEARCH_JSON_URL = APPLICATION_HOST
			+ "/petsitter/search/simple.json";
	
	public static String PETSITTER_PETDETAIL_URL = APPLICATION_HOST
			+ "/petsitter/petdetails/simple.json";

	public static String SEND_REQUEST_JSON_URL = APPLICATION_HOST
			+ "/petsitter/requests/";
	
	public static String REQUESTS_URL = APPLICATION_HOST
			+ "/petsitter/requests/simple.json";
	
	public static String UPLOAD_FILE_URL = APPLICATION_HOST
			+ "/petsitter/uploadfile";

	public static String DATE_FORMAT = "MM/dd/yyyy";
	
	public static String USERNAME = "userName";
	public static String PASSWORD = "password";
}
