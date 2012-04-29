package com.android.petswitch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.petswitch.R;

public class RequestList extends Activity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.requestresponselist);
		ListView reqreslist = (ListView) findViewById(R.id.reqreslist);
		List<RequestResponseDetails> reqDetails = new ArrayList<RequestResponseDetails>();
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		SetRequestDetails srd = new SetRequestDetails();
		reqDetails = srd.setRequestDetails();
		RequestListAdapter adapter = new RequestListAdapter(this, reqDetails);
		reqreslist.setAdapter(adapter);
		
		reqreslist.setOnItemClickListener(new OnItemClickListener() {
			
			//on item click handler
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent showDetail = new Intent(RequestList.this, RequestDetails.class);
				startActivity(showDetail);
			}

		});
	}

}