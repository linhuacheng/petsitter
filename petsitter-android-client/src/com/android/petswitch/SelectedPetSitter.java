package com.android.petswitch;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class SelectedPetSitter extends Activity {
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
}