package com.android.petswitch;

//import java.util.List;

import java.io.IOException;
import java.util.ArrayList;
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

import com.android.petswitch.dto.PetOwnerResult;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapPetOwnersActivity extends MapActivity {

	public static String PET_OWNERS = "PET_OWNERS";
	/** Called when the activity is first created. */
	// int selectedLat = 19240000,selectedLng = -99120000;

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

		Bundle extras = getIntent().getExtras();

		List<PetOwnerResult> petOwners = (ArrayList<PetOwnerResult>) extras
				.get(PET_OWNERS);

		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.map_icon);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(
				drawable, this);
		for (PetOwnerResult user : petOwners) {
			
			GeoPoint point = new GeoPoint(user.getLatitudeE6(),
					user.getLongitudeE6());
			String address1 = new StringBuilder()
					.append(isNotEmpty(user.getAddressLine1()) ? user
							.getAddressLine1() : "")
					.append(isNotEmpty(user.getAddressLine2()) ? (" " + user
							.getAddressLine2()) : "").toString();

			String address2 = new StringBuilder()
					.append(isNotEmpty(user.getCity()) ? (user.getCity()) : "")
					.append(isNotEmpty(user.getState()) ? (", " + user
							.getState()) : "")
					.append(isNotEmpty(user.getZip()) ? (" " + user.getZip())
							: "").toString();

			OverlayItem overlayitem = new OverlayItem(point, address1, address2);

			itemizedoverlay.addOverlay(overlayitem);
			
		}
		mapOverlays.add(itemizedoverlay);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isNotEmpty(String value) {
		return (value != null) && (value.length() > 0);
	}

}