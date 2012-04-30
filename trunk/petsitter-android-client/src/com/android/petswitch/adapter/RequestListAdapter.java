package com.android.petswitch.adapter;

import java.util.List;

import com.android.petswitch.R;
import com.android.petswitch.RequestResponseDetails;
import com.android.petswitch.R.drawable;
import com.android.petswitch.R.id;
import com.android.petswitch.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestListAdapter extends BaseAdapter {
	private final Context context;
	private List<RequestResponseDetails> requestDetails;

	public RequestListAdapter(Context context, List<RequestResponseDetails> requestDetails) {
		this.context = context;
		this.requestDetails = requestDetails;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		
		RequestResponseDetails reqDetails = new RequestResponseDetails();
		reqDetails = requestDetails.get(position);
		
		String s = reqDetails.getType();
		textView.setText(reqDetails.getRequest());

		if (s.startsWith("Request")) {
			imageView.setImageResource(R.drawable.receive_icon);
		} else {
			imageView.setImageResource(R.drawable.send_icon);
		}

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
}
