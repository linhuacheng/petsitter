package com.android.petswitch.util;

import android.content.SharedPreferences;


public class RestClientFactory {

	public static RestClient getSearchPetSitterClient(SharedPreferences pref) {
		return new RestClient (pref, ApplicationConstants.PETSITTER_SEARCH_JSON_URL);
	}
}
