package com.android.petswitch;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.petswitch.util.ApplicationConstants;
import com.android.petswitch.util.RequestMethod;
import com.android.petswitch.util.RestClient;
import com.android.petswitch.util.RestClientFactory;

public class RequestForm extends Activity {

	private TextView start_date;
	private TextView end_date;
	private TextView start_time;
	private TextView end_time;
	private EditText petType;
	private EditText message;
    private ImageButton mPickDate_start;
    private ImageButton mPickDate_end;
    private ImageButton mPickTime_start;
    private ImageButton mPickTime_end;
    private Button sendRequest;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private String userName;
    
    private boolean startdate = true;
    private boolean starttime = true;
    private int begin = 1; 
    
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    private static final String INNER_TAG = "SendRequest";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestform);
        
        
        
        userName = getIntent().getStringExtra("approverUserName");
        System.out.println("User Name is ... "+userName);
        
        start_date = (TextView) findViewById(R.id.req_startdate);
        end_date = (TextView) findViewById(R.id.req_enddate);
        start_time = (TextView) findViewById(R.id.req_starttime);
        end_time = (TextView) findViewById(R.id.req_endtime);
        petType = (EditText) findViewById(R.id.pet_type);
        message = (EditText) findViewById(R.id.request_message);
        
        mPickDate_start = (ImageButton) findViewById(R.id.select_start_date);
        mPickDate_end = (ImageButton) findViewById(R.id.select_end_date);
        mPickTime_start = (ImageButton) findViewById(R.id.select_start_time);
        mPickTime_end = (ImageButton) findViewById(R.id.select_end_time);
        sendRequest = (Button) findViewById(R.id.req_button);
        
        sendRequest.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				
				new DataThread().start();
				
				// crate alert dialog with request created message
				
				AlertDialog alertDialog = new AlertDialog.Builder(
                        RequestForm.this).create();
 
        // Setting Dialog Title
//        alertDialog.setTitle("Alert Dialog");
 
        // Setting Dialog Message
        alertDialog.setMessage("Request Sent Successfully!");
 
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ok);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(RequestForm.this, MobilePetSwitchActivity.class);
                intent.putExtra("currentTab", 1);
                startActivity(intent);
                
                }
        });
 
        // Showing Alert Message
        alertDialog.show();
        
            }
        });
        

        // add a click listener to the button
        mPickDate_start.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
			public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
                startdate = true;
            }
        });
        
        mPickDate_end.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
			public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
                startdate = false;
            }
        });

        mPickTime_start.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
			public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
                starttime = true;
            }
        });
        
        mPickTime_end.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
			public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
                starttime = false;
            }
        });
        
        
        
        
        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
          
     // get the current time
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        
        
          // display the current date
        updateDisplayDate();
        updateDisplayTime();
          
	}
	
    @Override
    protected Dialog onCreateDialog(int id) {
    	
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, false);   
        }
        return null;
    }
    
    

// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplayDate();
                }
            };

            
// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	    new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            mHour = hourOfDay;
	            mMinute = minute;
	            updateDisplayTime();
	        }
	    };            

	//updates the date in the TextView
	private void updateDisplayDate() {
		
		if(begin==1)
		{
			
		}
		else if(startdate){
			
			start_date.setText(
			        new StringBuilder()
			                // Month is 0 based so add 1
			                .append(mMonth + 1).append("/")
			                .append(mDay).append("/")
			                .append(mYear));
			start_date.requestFocus();
		}
		
		else
		{
			end_date.setText(
			        new StringBuilder()
			                // Month is 0 based so add 1
			                .append(mMonth + 1).append("/")
			                .append(mDay).append("/")
			                .append(mYear));
			end_date.requestFocus();

		}
   	}

	//updates the date in the TextView
	private void updateDisplayTime() {
		
		if(begin==1)
		{
			begin=0;
		}
		else if(starttime){
			
			start_time.setText(
					new StringBuilder()
	                .append(pad(mHour)).append(":")
	                .append(pad(mMinute)));
			start_time.requestFocus();
		}
		
		else
		{
			end_time.setText(
					new StringBuilder()
	                .append(pad(mHour)).append(":")
	                .append(pad(mMinute)));
			end_time.requestFocus();

		}
   	}
	

	private static String pad(int c) {
	    if (c >= 10)
	        return String.valueOf(c);
	    else
	        return "0" + String.valueOf(c);
	}

	
	class DataThread extends Thread {

		private static final String INNER_TAG = "getPetDetails";

		public void run() {

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);
			
			RestClient client = RestClientFactory
					.getRequestClient(prefs);
			
//			Log.i(INNER_TAG, user_name);
			
			client.addParam("petType", petType.getText().toString());
			System.out.println("User Name is ... "+userName);
			client.addParam("userName", userName);
			client.addParam("message", message.getText().toString());
			client.addParam("startDate", start_date.getText().toString());
			client.addParam("endDate", end_date.getText().toString());
			client.addParam("fromJson", "From Json");
			
			try {
				client.execute(RequestMethod.POST);

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
