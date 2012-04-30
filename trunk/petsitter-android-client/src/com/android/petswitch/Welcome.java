package com.android.petswitch;

import com.android.petswitch.util.ApplicationConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    	SharedPreferences prefs = getSharedPreferences(
				ApplicationConstants.USER_PREF, 0);
    	String username = prefs.getString(ApplicationConstants.USERNAME, "Empty");
    	
    	TextView welcomeUser = (TextView) findViewById(R.id.welcomeUser);
    	Button signIn = (Button) findViewById(R.id.welcomeUserButton);
    	if (username == null || username.length() ==0) {
    		welcomeUser.setText("Please sign in");
    	} else {
    		welcomeUser.setText("Welcome " +  username + "!");
    		signIn.setText("Switch user");
    	}
    }
	
	public void onClickWelcome(View v) {
		Intent i = new Intent(this, MobilePetSwitchActivity.class);
		startActivity(i);
    }
	
	public void onClickSetUser(View v) {
		Intent i = new Intent(this, SignInActivity.class);
		startActivity(i);
    }
	
}
