package com.android.petswitch;

import com.android.petswitch.util.ApplicationConstants;
import com.android.petswitch.util.RequestMethod;
import com.android.petswitch.util.RestClient;
import com.android.petswitch.util.RestClientFactory;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
	private String requestId;

	private String result = "new";

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.request_details);
		
		req_status = getIntent().getStringExtra("status");
		user_name = getIntent().getStringExtra("userName");
		pet_type = getIntent().getStringExtra("petType");
		req_start_date = getIntent().getStringExtra("reqStartDate");
		req_end_date = getIntent().getStringExtra("reqEndDate");
		message = getIntent().getStringExtra("comment");
		requestId = getIntent().getStringExtra("requestId");
		
		
		petType = (TextView) findViewById(R.id.pet_type);
		userName = (TextView) findViewById(R.id.pet_desc);
		reqStartDate = (TextView) findViewById(R.id.req_start_date);
		reqEndDate = (TextView) findViewById(R.id.req_end_date);
		comment = (TextView) findViewById(R.id.message);
		accept = (Button) findViewById(R.id.accept);
		reject = (Button) findViewById(R.id.reject);
		
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
			accept.setVisibility(Button.GONE);
			reject.setVisibility(Button.GONE);
			status = (TextView) findViewById(R.id.status);
			status.setText(req_status);
		}
	
		accept.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				updateRequest("Accepted");
				result = "Accepted";
				req_status = result;
				accept.setVisibility(Button.GONE);
				reject.setVisibility(Button.GONE);
				status = (TextView) findViewById(R.id.status);
				status.setText(req_status);
				
				setResult(RESULT_OK);
		         finish();
				
				
//				Intent intent = new Intent(RequestDetails.this, RequestList.class);
//				startActivity(intent);
			}
			
		});
		
		
		reject.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				updateRequest("Rejected");
				result = "Rejected";
				req_status = result;
				accept.setVisibility(Button.GONE);
				reject.setVisibility(Button.GONE);
				status = (TextView) findViewById(R.id.status);
				status.setText(req_status);
				
				setResult(RESULT_OK);
		        finish();
		         
//				Intent intent = new Intent(RequestDetails.this, RequestList.class);
//				startActivity(intent);
			}
			
		});
		
		
		Button b = (Button) findViewById(R.id.back_requestdetails);
		
		// set the on click listener for "Back to listings"
	    b.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	         
	        // when "back to listings" is clicked, go back to the real estate listings	 
	         setResult(RESULT_OK);
	         finish();
	         } 
	      });
		
	}
	
	public void updateRequest(String result)
	{
		new DataThread().start();
		return;
	}
	
	
	class DataThread extends Thread {

		private static final String INNER_TAG = "getPetDetails";

		public void run() {

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);
			
			RestClient client = RestClientFactory
					.getRequestClient(prefs);
			
//			Log.i(INNER_TAG, user_name);
			
			client.addParam("result", result);
			client.addParam("requestId", requestId);
			client.addParam("fromJson", "From Json");
			
			try {
				client.execute(RequestMethod.PUT);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
				}
				// return valid data
			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}
		}
	}
		
}
