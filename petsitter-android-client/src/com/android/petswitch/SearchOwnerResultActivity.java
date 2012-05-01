package com.android.petswitch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.petswitch.adapter.SearchPetOwnerAdapter;
import com.android.petswitch.dto.PetOwnerResult;
import com.android.petswitch.dto.SearchRequestBean;
import com.android.petswitch.util.ApplicationConstants;

public class SearchOwnerResultActivity extends Activity {
	
	public static String SEARCH_TYPE = "SEARCH_TYPE";
	public static String SEARCH_KEY = "SEARCH_KEY";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.petownerlist);	

		Bundle extras = getIntent().getExtras();
		String searchType = extras.getString(SEARCH_TYPE);
		String searchKey = extras.getString(SEARCH_KEY);
		
		SearchRequestBean searchCriteria = new SearchRequestBean();
		searchCriteria.addCriteria(searchType, searchKey);
		
		SharedPreferences prefs = getSharedPreferences(
				ApplicationConstants.USER_PREF, 0);
		// set the data adapter that will retrieve the data
		ListView ownerList = (ListView) findViewById(R.id.petownerlist);
		final SearchPetOwnerAdapter searchAdapter = new SearchPetOwnerAdapter(
				this, searchCriteria, prefs);
		ownerList.setAdapter(searchAdapter);

		// on click go to the view item widget
		ownerList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				System.out.println("Entering onClick of SearchOwnerResultActivity");
				PetOwnerResult petOwnerDetail = (PetOwnerResult) parent.getItemAtPosition(position);
				Intent intent = new Intent(view.getContext(),
						SelectedPetSitter.class);
				
				String displayName = petOwnerDetail.getDisplayName();
				System.out.println("Display Name of the user ... "+displayName);
				System.out.println("userName is .... "+petOwnerDetail.getUserName());
				
				intent.putExtra("userName", petOwnerDetail.getUserName());
				intent.putExtra("displayName",displayName);
				intent.putExtra("phoneNo", petOwnerDetail.getMobile());
				intent.putExtra("address1", petOwnerDetail.getAddressLine1());
				intent.putExtra("address2", petOwnerDetail.getAddressLine2());
				intent.putExtra("city", petOwnerDetail.getCity());
				intent.putExtra("state", petOwnerDetail.getState());
				intent.putExtra("zip", petOwnerDetail.getZip());
				
				System.out.println("Starting the activity");
				// intent.putExtra(ItemAdapter.ITEM_INDEX, position);
				startActivity(intent);
			}
		});

		// Turn on the progress bar AFTER the layout has been created.
		//setProgressBarIndeterminateVisibility(true);
	}

	public void gotoSelectedPetSitter(View v) {
		Intent i = new Intent(this, SelectedPetSitter.class);
		startActivity(i);
	}
}
