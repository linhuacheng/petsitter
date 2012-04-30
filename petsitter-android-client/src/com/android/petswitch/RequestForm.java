package com.android.petswitch;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class RequestForm extends Activity {

	private TextView start_date;
	private TextView end_date;
	private TextView start_time;
	private TextView end_time;
    private ImageButton mPickDate_start;
    private ImageButton mPickDate_end;
    private ImageButton mPickTime_start;
    private ImageButton mPickTime_end;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    
    private boolean startdate = true;
    private boolean starttime = true;
    private int begin = 1; 
    
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestform);
        
        
        start_date = (TextView) findViewById(R.id.req_startdate);
        end_date = (TextView) findViewById(R.id.req_enddate);
        start_time = (TextView) findViewById(R.id.req_starttime);
        end_time = (TextView) findViewById(R.id.req_endtime);
        
        mPickDate_start = (ImageButton) findViewById(R.id.select_start_date);
        mPickDate_end = (ImageButton) findViewById(R.id.select_end_date);
        mPickTime_start = (ImageButton) findViewById(R.id.select_start_time);
        mPickTime_end = (ImageButton) findViewById(R.id.select_end_time);
        
        

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

}
