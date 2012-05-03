package com.android.petswitch.adapter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.petswitch.R;
import com.android.petswitch.dto.PetOwnerResult;
import com.android.petswitch.dto.RequestResponseDetail;
import com.android.petswitch.dto.RequestResponseDetailRest;
import com.android.petswitch.util.ApplicationConstants;
import com.android.petswitch.util.RequestMethod;
import com.android.petswitch.util.RestClient;
import com.android.petswitch.util.RestClientFactory;
import com.google.gson.Gson;

public class RequestPickerAdapter extends BaseAdapter {
	private final Context context;
	private List<RequestResponseDetail> requestDetails;
	private SharedPreferences preference;
	private Handler threadHandler;
	
	public RequestPickerAdapter(Context ctx, SharedPreferences prefs) {
		this.context = ctx;
		preference = prefs;
		requestDetails = new ArrayList<RequestResponseDetail>();
		
		// the thread handler for asynchronous fetching of data
				threadHandler = new Handler() {

					public void handleMessage(Message msg) {

						requestDetails = (List<RequestResponseDetail>) msg.obj;
						if (requestDetails.size() ==0) {
							Toast.makeText(context, "No Requests found, please try again.",
									Toast.LENGTH_LONG).show();
						}
						
						notifyDataSetChanged();
						// activity.setProgressBarIndeterminateVisibility(false);
					}
				};

				new DataThread().start();
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.simple_request__rowlayout, parent, false);
		
		TextView textView = (TextView) rowView.findViewById(R.id.request_text);
		
		RequestResponseDetail reqDetails = new RequestResponseDetail();
		reqDetails = requestDetails.get(position);
		
		
//		textView.setText(reqDetails.getRequest());
		
		String approverUserName = reqDetails.getApproverUserName();
		String requestorUserName = reqDetails.getRequestorUserName();
		
		String currentUser = preference.getString(ApplicationConstants.USERNAME, "");
		
		textView.setText(requestorUserName + " - " + reqDetails.getRequestStartDate());
		

		return rowView;
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return requestDetails.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return requestDetails.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	class DataThread extends Thread {

		private static final String INNER_TAG = "DataThread";

		public void run() {

			Log.i(INNER_TAG, "Start parsing items");

			RestClient client = RestClientFactory
					.getListRequestClient(preference);
			
			String currentUser = preference.getString(ApplicationConstants.USERNAME, "");
			
			/*
			try {
				client.execute(RequestMethod.GET);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
				}
				// return valid data
				String jsonResult = client.getResponse();
				Log.i(INNER_TAG, jsonResult);
				Gson gson = new Gson();
				System.out.println("Before fromJson");
				RequestResponseDetailRest restResponse = gson.fromJson(jsonResult,
						RequestResponseDetailRest.class);
				System.out.println("After fromJson");
				System.out.println("restResponse..............."+restResponse);
				if ( restResponse.getRequestResponseDetails() != null)
				{
					List<RequestResponseDetail> tempDetails = restResponse.getRequestResponseDetails();
					requestDetails = new ArrayList<RequestResponseDetail>();
					for (RequestResponseDetail req: tempDetails) {
						String approverUserName = req.getApproverUserName();
						if (!currentUser.equalsIgnoreCase(approverUserName)) {
							requestDetails.add(req);
						}						
					}
					
					System.out.println("Request Response output size .... "+requestDetails.size());
				}
					
			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}
*/
			
			requestDetails = new ArrayList<RequestResponseDetail>();
			RequestResponseDetail r1 = new RequestResponseDetail();
			r1.setRequestorUserName("user1");
			r1.setRequestStartDate("12/24/2012");
			r1.setRequestId(new BigInteger("123"));
			
			RequestResponseDetail r2 = new RequestResponseDetail();
			r2.setRequestorUserName("user2");
			r2.setRequestStartDate("12/24/2012");
			r2.setRequestId(new BigInteger("123"));
			
			requestDetails.add(r1);
			requestDetails.add(r2);
			
			
			Log.i(INNER_TAG,
					"Done parsing items, send a message to the handler");

			// Send the parsing result to the handler.
			Message dataMsg = threadHandler.obtainMessage();
			dataMsg.obj = requestDetails;
			threadHandler.sendMessage(dataMsg);
		}
	}
}
