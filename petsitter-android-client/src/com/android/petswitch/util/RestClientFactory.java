package com.android.petswitch.util;

import android.content.SharedPreferences;


public class RestClientFactory {

	public static RestClient getSearchPetSitterClient(SharedPreferences pref) {
		return new RestClient (pref, ApplicationConstants.PETSITTER_SEARCH_JSON_URL);
	}
	
	public static RestClient getPetSitterPetDetailClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.PETSITTER_PETDETAIL_URL);
	}
	
	public static RestClient getRequestClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.SEND_REQUEST_JSON_URL);
	}
	
	public static RestClient getListRequestClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.REQUESTS_URL);
	}
	
	public static RestClient getUploadFileClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.UPLOAD_FILE_URL);
	}
	//SEND_REQUEST_JSON_URL
}
