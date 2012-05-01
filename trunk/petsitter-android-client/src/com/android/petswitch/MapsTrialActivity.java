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
	int selectedLat = 19240000,selectedLng = -99120000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	setContentView(R.layout.mapslayout);
//        Context context = null;
//        EditText text = new EditText(context);
//        String value = text.getText().toString();
//        Geocoder geoCoder = null;
//        int selectedLat = 0,selectedLng = 0;
//        
//        List<Address> addressList = null;
//		try {
//			addressList = geoCoder.getFromLocationName(value, 1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        Address address = addressList.get(0);
//        if(address.hasLatitude() && address.hasLongitude()){
//            selectedLat = (int) address.getLatitude();
//            selectedLng = (int) address.getLongitude();
//        }
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
        HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, this);
        GeoPoint point = new GeoPoint(selectedLat,selectedLng);
        OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}