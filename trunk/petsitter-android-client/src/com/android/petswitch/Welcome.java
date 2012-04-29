package com.android.petswitch;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Welcome extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
	}
	
	public void onClickWelcome(View v) {
		Intent i = new Intent(this, MobilePetSwitchActivity.class);
		startActivity(i);
    }
	
}
