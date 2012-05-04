package com.android.petswitch;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MobilePetSwitchActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        
//        System.out.println(getIntent().getPackage());
        
       
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, SearchActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("search").setIndicator("",
        		res.getDrawable(R.drawable.dog_search_trans)) 
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, RequestList.class);
        spec = tabHost.newTabSpec("reqres").setIndicator(null,
        		res.getDrawable(R.drawable.dog_with_message_trans))
                      .setContent(intent);
        tabHost.addTab(spec);
        intent = new Intent().setClass(this, CameraPreview.class);
        spec = tabHost.newTabSpec("capture").setIndicator("",
        		res.getDrawable(R.drawable.dog_camera_trans))
                      .setContent(intent);
        tabHost.addTab(spec);
        
//        if(getIntent().getExtras().getInt("currentTab")==1)
//        	tabHost.setCurrentTab(1);
//        else
        	tabHost.setCurrentTab(0);

    }
}