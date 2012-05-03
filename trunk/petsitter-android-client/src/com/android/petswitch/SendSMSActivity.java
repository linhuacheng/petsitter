package com.android.petswitch;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMSActivity extends Activity{
	
    private String phone_number;
    
    private final static String MESSAGE_DELIVERY = "Message sent successfully";
    
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendsms);     // set the content view to sendsms layout
		
		phone_number = getIntent().getStringExtra("phone_no");
        
        final EditText message_content = (EditText) SendSMSActivity.this
                .findViewById(R.id.sms_content);
        final TextView message_status = (TextView) SendSMSActivity.this.findViewById(R.id.sms_status);

        // Watch for send button clicks and send text messages.
        Button sendButton = (Button) findViewById(R.id.sms_send_message);
        sendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(message_content.getText())) {
                    Toast.makeText(SendSMSActivity.this, "Please enter a message body.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Disable the message body field during the message delivery
                message_content.setEnabled(false);

                //create SmsManager object
                SmsManager sms = SmsManager.getDefault();
                //get the content in message body
                String message = message_content.getText().toString();
                //send the message
                sms.sendTextMessage(phone_number, null, message, PendingIntent.getBroadcast(
                        SendSMSActivity.this, 0, new Intent(MESSAGE_DELIVERY), 0), null);
            }
        });


        
        TextView b = (TextView) findViewById(R.id.close_sms);
		
		// set the on click listener for "Back to listings"
	      b.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	         
	        // when "back to listings" is clicked, go back to the real estate listings	 
	         setResult(RESULT_OK);
	         finish();
	         } 
	      });

	      
	   // Register broadcast receivers for SMS sent and delivered intents
	        registerReceiver(new BroadcastReceiver() {
	            @Override
	            public void onReceive(Context context, Intent intent) {
	                String message = null;
	                boolean error = true;
	                switch (getResultCode()) {
	                case Activity.RESULT_OK:
	                    message = "Message sent successfully!"; // display this text when message is sent successfully 
	                    error = false;
	                    break;
	                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
	                    message = "Error due to failure";
	                    break;
	                case SmsManager.RESULT_ERROR_NO_SERVICE:
	                    message = "Error due to No service.";
	                    break;
	                case SmsManager.RESULT_ERROR_NULL_PDU:
	                    message = "Error due to Null PDU.";
	                    break;
	                case SmsManager.RESULT_ERROR_RADIO_OFF:
	                    message = "Error: Radio is off.";
	                    break;
	                }

	                message_content.setEnabled(true);
	                message_content.setText("");

	                message_status.setText(message);
	                message_status.setTextColor(error ? Color.RED : Color.GREEN);
	            }
	        }, new IntentFilter(MESSAGE_DELIVERY));
	}
}
	