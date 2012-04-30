package com.android.petswitch;

import com.android.petswitch.util.ApplicationConstants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignInActivity extends Activity {

	private EditText nameBox;
	private EditText passwordBox;
	    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_form);
                
	    nameBox = (EditText) findViewById(R.id.name_box);
	    passwordBox = (EditText) findViewById(R.id.password_box);               
	}
	
	public void onClickWelcome(View v) {
		SharedPreferences settings = getSharedPreferences(ApplicationConstants.USER_PREF, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(ApplicationConstants.USERNAME, nameBox.getText().toString());
	    editor.putString(ApplicationConstants.PASSWORD, passwordBox.getText().toString());
	        
	     editor.commit();
		    
		Intent i = new Intent(this, Welcome.class);
		startActivity(i);
    }
		
}
