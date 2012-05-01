package com.android.petswitch;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.petswitch.dto.PetDetailBean;
import com.android.petswitch.dto.PetDetailResultRest;
import com.android.petswitch.dto.PetOwnerResult;
import com.android.petswitch.util.ApplicationConstants;
import com.android.petswitch.util.RequestMethod;
import com.android.petswitch.util.RestClient;
import com.android.petswitch.util.RestClientFactory;
import com.google.gson.Gson;

public class SelectedPetSitter extends Activity {
	
	private Handler threadHandler;
	private String displayName;
	private TextView userName;
	private TextView phoneNo;
	private TextView address;
	private TextView petName;
	private TextView petType;
	private TextView petDesc;
	
	private String display_name;
	private String phone_no;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String user_name;
	
	List<PetDetailBean> userPetDetails = new ArrayList<PetDetailBean>(); 
	PetDetailBean pdb = new PetDetailBean();
	PetOwnerResult petOwnerDetail;
	
    /** Called when the activity is first created. */
	private String[] album1 = {
			"http://gi0001.photobucket.com/groups/0001/F9P8EG7YR8/Elsa.jpg",
			"http://i1061.photobucket.com/albums/t467/cindypumpkin/healthy_yellow_labrador_puppies_.jpg",
			"http://i744.photobucket.com/albums/xx90/lunoza/Labrador.jpg",
			"http://i1061.photobucket.com/albums/t467/cindypumpkin/labrador_retriever.jpg"};
	private SlideShow ss;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        System.out.println("Entered SelectedPetSitter...");
        petName = (TextView) findViewById(R.id.pet_name);
        petType = (TextView) findViewById(R.id.pet_type);
        petDesc = (TextView) findViewById(R.id.pet_desc);
        
        user_name = getIntent().getStringExtra("userName");
        System.out.println("user_name is ... "+user_name);
     // the thread handler for asynchronous fetching of data
     		threadHandler = new Handler() {

     			public void handleMessage(Message msg) {

     				System.out.println("In the handleMessage()");
     				userPetDetails = (List<PetDetailBean>) msg.obj;
     				if (userPetDetails.size() ==0) {
     					Toast.makeText(getBaseContext(), "No Pet Detail found, please try again.",
     							Toast.LENGTH_LONG).show();
     				} else {
     				      if(userPetDetails.size()!=0)
     				        {
     				        	pdb = userPetDetails.get(0);
     				        	petName.setText(pdb.getPetName());
     				            petType.setText(pdb.getPetType());
     				            petDesc.setText(pdb.getPetDesc());
     				        }
     				}
     				
     				notifyDataSetChanged();
     				// activity.setProgressBarIndeterminateVisibility(false);
     			}

				private void notifyDataSetChanged() {
					// TODO Auto-generated method stub
					
				}
     		};

     		System.out.println("Starting Data Thread");
     		new DataThread().start();
     		
        
        displayName = getIntent().getStringExtra("displayName");
        SelectedPetSitterDetails petSitterDetail = new SelectedPetSitterDetails();
        petOwnerDetail = petSitterDetail.getPetOwner();
        
        userName = (TextView) findViewById(R.id.user_name);
        phoneNo = (TextView) findViewById(R.id.phone_no);
        address = (TextView) findViewById(R.id.user_address);
        
        
        display_name = getIntent().getStringExtra("displayName");
        phone_no = getIntent().getStringExtra("phoneNo");
        address1 = getIntent().getStringExtra("address1");
        address2 = " "+getIntent().getStringExtra("address2");
        city = ", "+getIntent().getStringExtra("city");
        state = ", "+getIntent().getStringExtra("state");
        zip = " "+getIntent().getStringExtra("zip");
        
        
        userName.setText(display_name);
        phoneNo.setText(phone_no);
        address.setText(address1+address2+city+state+zip);
        
        if(userPetDetails.size()!=0)
        {
        	pdb = userPetDetails.get(0);
        	petName.setText(pdb.getPetName());
            petType.setText(pdb.getPetType());
            petDesc.setText(pdb.getPetDesc());
        }
        
        
    }
    
    public void callMap(View v){
    	Intent i = new Intent(SelectedPetSitter.this, MapsTrialActivity.class);
        startActivity(i);
    }
    
    public void viewSlideshow() throws MalformedURLException {
		
		ss = (SlideShow) findViewById(R.id.slide_show);
		ss.setSlideShowListener(new SlideShow.SlideShowListener() {
			// displays the content that slideshow is loading
			public void onLoadSlidesEnd(SlideShow slideshow) {
				System.out.println("-----");
				Toast.makeText(SelectedPetSitter.this,
						R.string.label_ss_load_end, Toast.LENGTH_SHORT).show();
			}

			// displays the content that slideshow is loaded
			public void onLoadSlidesStart(SlideShow slideshow) {
				System.out.println("-----");
				Toast.makeText(SelectedPetSitter.this,
						R.string.label_ss_load_start, Toast.LENGTH_SHORT)
						.show();
			}
		});

		ImageButton buttPrev = (ImageButton) findViewById(R.id.butt_slide_main_prev);
		ImageButton buttNext = (ImageButton) findViewById(R.id.butt_slide_main_next);
		buttNext.setOnClickListener(new View.OnClickListener() {
			// depending on which button is clicked display the image in the
			// linked list
			public void onClick(View arg0) {
				try {
					ss.drawNextSlide();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		buttPrev.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				try {
					ss.drawPrevSlide();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		// get slides
		ss.setSlides(getSlides());
		ss.loadSlides();
	}

	private LinkedList<DrawableSlide> getSlides() throws MalformedURLException {
		LinkedList<DrawableSlide> slides = new LinkedList<DrawableSlide>();
		String[] album = album1;
		// define list of resource ids
		
		URL[] slideUrl = new URL[album.length];
		for (int i = 0; i < album.length; i++) {
			slideUrl[i] = new URL(album[i]);
			slides.add(new DrawableSlide(slideUrl[i], true));
		}
		return slides;
	}
	
	public void gotoRequestForm(View v)
	{
		Intent i = new Intent(this, RequestForm.class);
		startActivity(i);
		
	}
	
	class DataThread extends Thread {

		private static final String INNER_TAG = "getPetDetails";

		public void run() {

			Log.i(INNER_TAG, "Start parsing items");

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);
			
			RestClient client = RestClientFactory
					.getPetSitterPetDetailClient(prefs);
			
			Log.i(INNER_TAG, user_name);
			
			client.addParam("userName", user_name);
			
			try {
				client.execute(RequestMethod.GET);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
				}
				// return valid data
				String jsonResult = client.getResponse();
				Log.i("getPetDetails", jsonResult);
				Gson gson = new Gson();
				PetDetailResultRest restResponse = gson.fromJson(jsonResult,
						PetDetailResultRest.class);
				if ( restResponse.getUserPetDetails() != null)
					userPetDetails = restResponse.getUserPetDetails();
			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			
			Log.i(INNER_TAG,
					"Done parsing items, send a message to the handler");

			// Send the parsing result to the handler.
			Message dataMsg = threadHandler.obtainMessage();
			dataMsg.obj = userPetDetails;
			threadHandler.sendMessage(dataMsg);
		}
	}
}