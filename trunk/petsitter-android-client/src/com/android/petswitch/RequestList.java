package com.android.petswitch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.petswitch.adapter.RequestListAdapter;
import com.android.petswitch.dto.RequestResponseDetail;
import com.android.petswitch.util.ApplicationConstants;
import com.android.petswitch.util.FileCacheUtil;

public class RequestList extends Activity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.requestresponselist);
		ListView reqreslist = (ListView) findViewById(R.id.reqreslist);
		
		final SharedPreferences prefs = getSharedPreferences(ApplicationConstants.USER_PREF, 0);
		RequestListAdapter adapter = new RequestListAdapter(this, prefs);
		reqreslist.setAdapter(adapter);
		
		reqreslist.setOnItemClickListener(new OnItemClickListener() {
			
			//on item click handler
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				System.out.println("Entering onClick of RequestList");
				RequestResponseDetail requestResponseDetail = (RequestResponseDetail) parent.getItemAtPosition(position);
				if (RequestResponseDetail.IREQ.equalsIgnoreCase(requestResponseDetail.getType()) || 
						RequestResponseDetail.OREQ.equalsIgnoreCase(requestResponseDetail.getType())){
					Intent intent = new Intent(view.getContext(),
							RequestDetails.class);
					
					System.out.println("Requestor userName is .... "+requestResponseDetail.getRequestorUserName());
					
					intent.putExtra("petType", requestResponseDetail.getPetType());
					intent.putExtra("reqStartDate", requestResponseDetail.getRequestStartDate());
					intent.putExtra("reqEndDate", requestResponseDetail.getRequestEndDate());
					intent.putExtra("comment", requestResponseDetail.getComment());
					intent.putExtra("status", requestResponseDetail.getStatus());
					intent.putExtra("requestId", requestResponseDetail.getRequestId().toString());
					
					
					if(requestResponseDetail.getRequestorUserName().equalsIgnoreCase(prefs.getString(ApplicationConstants.USERNAME, "")))
					{
						intent.putExtra("userName", requestResponseDetail.getApproverUserName());
						
						intent.putExtra("phoneNo", requestResponseDetail.getApproverPhoneNumber());
						
						intent.putExtra("requestType", "sent");
					}
					else
					{
						intent.putExtra("userName", requestResponseDetail.getRequestorUserName());
						
						intent.putExtra("phoneNo", requestResponseDetail.getRequesterPhoneNumber());
						
						intent.putExtra("requestType", "received");
					}
					
					startActivity(intent);

				} else if (RequestResponseDetail.IRES.equalsIgnoreCase(requestResponseDetail.getType()) || 
						RequestResponseDetail.ORES.equalsIgnoreCase(requestResponseDetail.getType())){
					Intent intent = new Intent(view.getContext(),
							ImageGalleryActivity.class);
					intent.putExtra("type", FileCacheUtil.getMediaType(requestResponseDetail.getContentType()));
					intent.putExtra("fileName", requestResponseDetail.getFileName());
					startActivity(intent);
				}
			}

		});
	}

}