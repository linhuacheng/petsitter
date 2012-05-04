package com.android.petswitch;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * simple video view to play videos
 * 
 * @author ckempaiah
 *
 */
public class SimpleVideoView extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoview);
        //getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
	        VideoView videoView = (VideoView)findViewById(R.id.videoView);
	        MediaController mediaController = new MediaController(this);
	        //mediaController.setAnchorView(videoView);
	        videoView.setMediaController(mediaController);
	        
	        //videoView.setVideoURI(Uri.parse("http://hermes.sprc.samsung.pl/widget/tmp/testh.3gp"));
	        //videoView.setVideoPath("/sdcard/ga.3gp");
	        String videoPath = extras.getString("mediapath");
	        videoView.setVideoURI(Uri.parse(videoPath));
	        videoView.requestFocus();
	        videoView.start();  
        } else {
        	Toast.makeText(SimpleVideoView.this, "Invalid Video Input", Toast.LENGTH_SHORT).show();
        }
    }


}
