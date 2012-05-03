package com.android.petswitch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RequestDetails extends Activity {
	
	private TextView petType;
	private TextView reqStartDate;
	private TextView reqEndDate;
	private TextView comment;
	private TextView userName;
	private TextView status;
	
	private Button accept;
	private Button reject;
	
	private String req_status;
	private String user_name;
	private String pet_type;
	private String req_start_date;
	private String req_end_date;
	private String message; 

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.request_details);
		
		req_status = getIntent().getStringExtra("status");
		user_name = getIntent().getStringExtra("userName");
		pet_type = getIntent().getStringExtra("petType");
		req_start_date = getIntent().getStringExtra("reqStartDate");
		req_end_date = getIntent().getStringExtra("reqEndDate");
		message = getIntent().getStringExtra("comment");
		
		
		petType = (TextView) findViewById(R.id.pet_type);
		userName = (TextView) findViewById(R.id.pet_desc);
		reqStartDate = (TextView) findViewById(R.id.req_start_date);
		reqEndDate = (TextView) findViewById(R.id.req_end_date);
		comment = (TextView) findViewById(R.id.message);
		
		petType.setText(pet_type);
		userName.setText(user_name);
		reqStartDate.setText(req_start_date);
		reqEndDate.setText(req_end_date);
		comment.setText(message);
		
		if(req_status.equalsIgnoreCase("new"))
		{
			
		}
		else
		{
			accept = (Button) findViewById(R.id.accept);
			reject = (Button) findViewById(R.id.reject);
			
			accept.setVisibility(Button.INVISIBLE);
			reject.setVisibility(Button.INVISIBLE);
			status = (TextView) findViewById(R.id.status);
			status.setText(req_status);
		}
		
	}
}
