package com.sjsu.petsitter.service;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;



public class GeocodingService {
	
	
	public static LatLng getLocation(String address) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		
		if (geocoderResponse.getResults() != null && geocoderResponse.getResults().size() >0 ) {
			return geocoderResponse.getResults().get(0).getGeometry().getLocation();
		} else {
			return new LatLng();
		}
		
		
	}
	
	public static void main(String[] args) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress("Paris, France").setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		
		LatLng loc = geocoderResponse.getResults().get(0).getGeometry().getLocation();
		
		
		System.out.println("lat" + loc.getLat());
		System.out.println("long" + loc.getLng());
		
	}
    
}
