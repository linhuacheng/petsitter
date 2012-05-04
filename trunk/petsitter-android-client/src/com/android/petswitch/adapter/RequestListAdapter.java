package com.android.petswitch.adapter;

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
import com.android.petswitch.dto.RequestResponseDetail;
import com.android.petswitch.dto.RequestResponseDetailRest;
import com.android.petswitch.util.ApplicationConstants;
import com.android.petswitch.util.RequestMethod;
import com.android.petswitch.util.RestClient;
import com.android.petswitch.util.RestClientFactory;
import com.google.gson.Gson;

public class RequestListAdapter extends BaseAdapter {
	private final Context context;
	private List<RequestResponseDetail> requestDetails;
	private SharedPreferences preference;
	private Handler threadHandler;

	
	public RequestListAdapter(Context ctx, SharedPreferences prefs) {
		this.context = ctx;
		preference = prefs;
		requestDetails = new ArrayList<RequestResponseDetail>();
		
		// the thread handler for asynchronous fetching of data
		threadHandler = new Handler() {

			public void handleMessage(Message msg) {

				requestDetails = (List<RequestResponseDetail>) msg.obj;
				if (requestDetails.size() == 0) {
					Toast.makeText(context,
							"No Requests found, please try again.",
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

		View rowView = inflater.inflate(R.layout.rowlayout, parent,
				false);

		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

		RequestResponseDetail reqDetails = new RequestResponseDetail();
		reqDetails = requestDetails.get(position);

		// textView.setText(reqDetails.getRequest());

		String approverUserName = reqDetails.getApproverUserName();
		String requestorUserName = reqDetails.getRequestorUserName();

		String currentUser = preference.getString(
				ApplicationConstants.USERNAME, "");

		if (RequestResponseDetail.IREQ.equalsIgnoreCase(reqDetails.getType())) {
			imageView.setImageResource(R.drawable.receive_icon);
			textView.setText("From:" + reqDetails.getRequestorUserName());
			ViewGroup viewGroup = (ViewGroup)rowView.findViewById(R.id.linearLabel);
			addTextView(viewGroup, "Start Date:" + reqDetails.getRequestStartDate() + " End Date:" + reqDetails.getRequestEndDate());
			addTextView(viewGroup, "Pet Type: " + reqDetails.getPetType());
		} else if (RequestResponseDetail.OREQ.equalsIgnoreCase(reqDetails
				.getType())) {
			imageView.setImageResource(R.drawable.send_icon);
			textView.setText("To:" + reqDetails.getApproverUserName());
			ViewGroup viewGroup = (ViewGroup)rowView.findViewById(R.id.linearLabel);
			addTextView(viewGroup, "Start Date:" + reqDetails.getRequestStartDate() + " End Date:" + reqDetails.getRequestEndDate());
			addTextView(viewGroup, "Pet Type: " + reqDetails.getPetType());
		} else if (RequestResponseDetail.IRES.equalsIgnoreCase(reqDetails
				.getType())
				|| RequestResponseDetail.ORES.equalsIgnoreCase(reqDetails
						.getType())) {
			if (reqDetails.getContentType() ==null) {
				reqDetails.setContentType("");
			}
			if (reqDetails.getContentType().toLowerCase()
					.matches("img|jpg|png|bmp")) {
				imageView.setImageResource(R.drawable.img_icon);
			} else if (reqDetails.getContentType().toLowerCase()
					.matches("mp3|mp4|3gp|avi")){
				imageView.setImageResource(R.drawable.video_icon);
			} else{
				imageView.setImageResource(R.drawable.text_icon);
			}
			textView.setText("From: " + reqDetails.getRequestorUserName());
			ViewGroup viewGroup = (ViewGroup)rowView.findViewById(R.id.linearLabel);
			addTextView(viewGroup, "Response Date:" + reqDetails.getRequestStartDate());
			addTextView(viewGroup, reqDetails.getComment());

		}

		return rowView;

	}
	private void addTextView(ViewGroup viewGroup, String text){

		TextView textView = new TextView(viewGroup.getContext());
		textView.setTextSize(10);
		textView.setTextAppearance(context, R.style.TextFieldStyle);
		textView.setText(text);
		viewGroup.addView(textView);

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
				RequestResponseDetailRest restResponse = gson.fromJson(
						jsonResult, RequestResponseDetailRest.class);
				System.out.println("After fromJson");
				System.out
						.println("restResponse..............." + restResponse);
				if (restResponse.getRequestResponseDetails() != null) {
					requestDetails = restResponse.getRequestResponseDetails();
					System.out.println("Request Response output size .... "
							+ requestDetails.size());
				}

			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			Log.i(INNER_TAG,
					"Done parsing items, send a message to the handler");

			// Send the parsing result to the handler.
			Message dataMsg = threadHandler.obtainMessage();
			dataMsg.obj = requestDetails;
			threadHandler.sendMessage(dataMsg);
		}
	}
	

}
