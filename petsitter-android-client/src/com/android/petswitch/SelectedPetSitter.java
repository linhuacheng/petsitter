package com.android.petswitch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
	private TextView userName;
	private TextView phoneNo;
	private TextView address;
	private TextView petName;
	private TextView petType;
	private TextView petDesc;

	LayoutInflater linflater;
    LinearLayout l;
    
	private String display_name;
	private String phone_no;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private static String user_name;
	private double latitude;
	private double longitude;
	private String full_address1;
	private String full_address2;
	
	static List<PetDetailBean> petDetails = new ArrayList<PetDetailBean>();
	
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
	
	public ViewFlipper mFlipper = null;

	private Integer[] mImageIds = {
            R.drawable.list1_1,
            R.drawable.list1_2,
            R.drawable.list1_3,
            R.drawable.list1_4};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        System.out.println("Entered SelectedPetSitter...");

        
      mFlipper = ((ViewFlipper) this.findViewById(R.id.flipper2));
        
      // add the the images to the flipper
      for(int i=0;i<mImageIds.length;i++)
      {
          setFlipperImage(mImageIds[i]);
      }
      
      startFlip();

        
        
        
        petName = (TextView) findViewById(R.id.pet_name);
        petType = (TextView) findViewById(R.id.pet_type);
        petDesc = (TextView) findViewById(R.id.pet_desc);
        l = (LinearLayout) findViewById(R.id.pet_linear_layout);
        
        user_name = getIntent().getStringExtra("userName");
        
        
        latitude = getIntent().getExtras().getDouble("latitude");
        longitude = getIntent().getExtras().getDouble("longitude");
//       
        
        System.out.println("Latitude in search is................."+latitude);
        System.out.println("Longitude in search is................."+longitude);
        
        System.out.println("user_name before thread handler is ... "+user_name);
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
     				    	  	SelectedPetSitter.petDetails = userPetDetails;
//     				    	  	addTextView(l, "Pet 1: ");
  				    	 		pdb = userPetDetails.get(0);
  				    	 		petType.setText(pdb.getPetType()+" - "+pdb.getPetName());
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

     		System.out.println("user_name after thread handler is ... "+user_name);
     		
     		System.out.println("Starting Data Thread");
     		new DataThread().start();
     		
        
        
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
        
        full_address1 = address1+address2;
        full_address2 = city+state+zip;
        
        userName.setText(display_name);
        SpannableString content = new SpannableString(phone_no);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        phoneNo.setText(content);
        address.setText(address1+address2+city+state+zip);
        
        
        registerForContextMenu(phoneNo);
        
        Button b = (Button) findViewById(R.id.back_details);
		
		b.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	         
	        // when "back to listings" is clicked, go back to the real estate listings	 
	         setResult(RESULT_OK);
	         finish();
	         } 
	      });
        
        System.out.println("User Name at the end of onCreate Method is..."+user_name);
        
    }
    
    
    private void addTextView(LinearLayout l, String text){

		TextView textView = new TextView(this);
		textView.setTextSize(10);
		textView.setTextAppearance(this.getBaseContext(), R.style.DescFont);
		textView.setText(text);
		l.setDividerPadding(1);
		l.addView(textView);

	}
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo){

			menu.setHeaderTitle("Menu");
			menu.add(0, v.getId(), 0, "Send SMS");
			menu.add(0, v.getId(), 0, "Call Pet Sitter");
		
		super.onCreateContextMenu(menu, v, menuInfo);
		
		// Set the menu options 
		
		
    }
	
	
	@Override  
	public boolean onContextItemSelected(MenuItem item) { 
		
		// call respective functions when an option in the menu is selected
	    if(item.getTitle()=="Send SMS"){sendSMS(item.getItemId());}
	    else if(item.getTitle()=="Call Pet Sitter"){callOwner(item.getItemId());}
	    else {return false;}  
	return true;  
	}  
	
	public void sendSMS(int rid)
	{
		Intent i = new Intent(SelectedPetSitter.this, SendSMSActivity.class);    // call SendSMSActivity activity when Send SMS option is selected 
        i.putExtra("phone_no", phone_no);  //phone_no
		startActivity(i);
	}

	public void callOwner(int rid)
	{
		Intent phoneCall = new Intent(Intent.ACTION_CALL);	// call ACTION_CALL intent when Call option is selected
		phoneCall.setData(Uri.parse("tel:"+phone_no));					
		startActivity(phoneCall);
	}

	
	
	
    
    public void callMap(View v){
    	Intent i = new Intent(SelectedPetSitter.this, MapsTrialActivity.class);
    	i.putExtra("latitude", latitude);
    	i.putExtra("longitude", longitude);
    	System.out.println("Latitude before calling maps activity is................."+latitude);
        System.out.println("Longitude before calling maps activity is................."+longitude);
    	i.putExtra("address1", full_address1);
    	i.putExtra("address2", full_address2);
        startActivity(i);
    }
    
 	
	public void gotoRequestForm(View v)
	{
		Intent i = new Intent(this, RequestForm.class);
		i.putExtra("approverUserName", user_name);//getIntent().getStringExtra("userName")
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
	
	
	
	// create the image view and add the view to the flipper
	private void setFlipperImage(int res) {
	    ImageView image = new ImageView(getApplicationContext());
	    image.setBackgroundResource(res);
	    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100,100,Gravity.CENTER);
	    image.setLayoutParams(params);
	    mFlipper.addView(image);
	}

	
	private void startFlip()
	{
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_out));
        mFlipper.startFlipping();
        mFlipper.setFlipInterval(1300);
	}
	

}