package com.android.petswitch.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.petswitch.R;
import com.android.petswitch.dto.PetOwnerResult;
import com.android.petswitch.dto.PetOwnerResultRest;
import com.android.petswitch.dto.SearchRequestBean;
import com.android.petswitch.util.RequestMethod;
import com.android.petswitch.util.RestClient;
import com.android.petswitch.util.RestClientFactory;
import com.google.gson.Gson;

public class SearchPetOwnerAdapter extends BaseAdapter {

	private List<PetOwnerResult> petOwners;
	private final Context context;
	private Handler threadHandler;
	private SearchRequestBean searchRequestBean;
	private SharedPreferences preference;

	public SearchPetOwnerAdapter(Context ctx, SearchRequestBean filter,
			SharedPreferences pref) {
		petOwners = new ArrayList<PetOwnerResult>();
		searchRequestBean = filter;
		preference = pref;
		this.context = ctx;

		// the thread handler for asynchronous fetching of data
		threadHandler = new Handler() {

			public void handleMessage(Message msg) {

				petOwners = (List<PetOwnerResult>) msg.obj;
				if (petOwners.size() ==0) {
					Toast.makeText(context, "No Pet Sitter found, please try again.",
							Toast.LENGTH_LONG).show();
				}
				
				notifyDataSetChanged();
				// activity.setProgressBarIndeterminateVisibility(false);
			}
		};

		new DataThread().start();
	}

	public int getCount() {
		return petOwners.size();
	}

	public Object getItem(int position) {
		return petOwners.get(position);
	}

	public long getItemId(int position) {
		return petOwners.get(position).getId().longValue();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("", "Get Item view - " + position);

		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.petowner_rowlayout, parent,
					false);
		}

		PetOwnerResult petOwner = petOwners.get(position);

		if (petOwner != null) {
			TextView userNameText = (TextView) rowView
					.findViewById(R.id.userName);
			TextView userPhoneText = (TextView) rowView
					.findViewById(R.id.userPhone);
			TextView userAddressText = (TextView) rowView
					.findViewById(R.id.userAddress);

			String phone = petOwner.getMobile();
			if (phone == null || phone.length() == 0)
				phone = petOwner.getHomePhone();

			String address = new StringBuilder()
					.append(isNotEmpty(petOwner.getAddressLine1()) ? petOwner
							.getAddressLine1() : "")					
					.append(isNotEmpty(petOwner.getAddressLine2()) ? ( " " + petOwner.getAddressLine2()) : "")
					.append(isNotEmpty(petOwner.getCity()) ? ( ", " + petOwner.getCity())
							: "")					
					.append(isNotEmpty(petOwner.getState()) ? ( ", " + petOwner.getState())
							: "").toString();

			userNameText.setText("Username: " + petOwners.get(position).getDisplayName());
			userPhoneText.setText("Phone: " + phone);
			userAddressText.setText("Address: " + address);
		}

		return rowView;
	}

	class DataThread extends Thread {

		private static final String INNER_TAG = "DataThread";

		public void run() {

			Log.i(INNER_TAG, "Start parsing items");

			RestClient client = RestClientFactory
					.getSearchPetSitterClient(preference);
			searchRequestBean.addRestClientParam(client);
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
				PetOwnerResultRest restResponse = gson.fromJson(jsonResult,
						PetOwnerResultRest.class);
				if ( restResponse.getPetOwners() != null)
					petOwners = restResponse.getPetOwners();
			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString(), e);
			}

			
			Log.i(INNER_TAG,
					"Done parsing items, send a message to the handler");

			// Send the parsing result to the handler.
			Message dataMsg = threadHandler.obtainMessage();
			dataMsg.obj = petOwners;
			threadHandler.sendMessage(dataMsg);
		}
	}

	private boolean isNotEmpty(String value) {
		return (value != null) && (value.length() > 0);
	}

}
