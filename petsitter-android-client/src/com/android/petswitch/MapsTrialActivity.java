package com.android.petswitch;

//import java.util.List;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
//import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapsTrialActivity extends MapActivity {
    /** Called when the activity is first created. */
//	int selectedLat = 19240000,selectedLng = -99120000;

	int constMultiplier = 1000000;
	private int selectedLat;
	private int selectedLng;
	private double user_lat;
	private double user_lon;
	private String address1;
	private String address2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	setContentView(R.layout.mapslayout);
    	
    	
    	user_lat = (getIntent().getExtras().getDouble("latitude")) * constMultiplier;
    	user_lon = (getIntent().getExtras().getDouble("longitude")) * constMultiplier;
    	System.out.println("Latitude in maps activity is................."+user_lat);
        System.out.println("Longitude in maps activity is................."+user_lon);
    	address1 = getIntent().getStringExtra("address1");  
    	address2 = getIntent().getStringExtra("address2");
    	
    	selectedLat = (int) user_lat;
    	selectedLng = (int) user_lon;
    	
    	

       System.out.println("Latitude is................."+selectedLat);
       System.out.println("Longitude is................."+selectedLng);
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.map_icon);
        HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, this);
        GeoPoint point = new GeoPoint(selectedLat, selectedLng);
        OverlayItem overlayitem = new OverlayItem(point, address1, address2);
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}