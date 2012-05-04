package com.android.petswitch.adapter;

import java.util.List;

import com.android.petswitch.R;
import com.android.petswitch.R.drawable;
import com.android.petswitch.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class VideoAdapter extends BaseAdapter {
	private Context ctx;
	private String listingId;
	private List<String> mediaList;

	int mGalleryItemBackground;
	

	public VideoAdapter(Context ctx, String listingId, List<String> mediaList){
		this.listingId = listingId;
		this.mediaList = mediaList;
		TypedArray attr = ctx.obtainStyledAttributes(R.styleable.ImageGalleryView);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.ImageGalleryView_android_galleryItemBackground, 0);
        attr.recycle();

		this.ctx = ctx;
		
	}
	//@Override
	public int getCount() {
		return mediaList.size();
	}

	//@Override
	public Object getItem(int position) {
		return null;
	}

	//@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView = null;
		if(convertView == null){
			// construct image view used to render the grid
			imageView = new ImageView(ctx);
			imageView.setAdjustViewBounds(true);
			imageView.setLayoutParams(new Gallery.LayoutParams(90, 60));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setBackgroundResource(mGalleryItemBackground);
	        //imageView.setBackgroundResource(R.drawable.galleryborder);
			//imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			//imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			//imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		String fileName = mediaList.get(position);
		int imageIcon ;
		
		imageIcon = R.drawable.movie;
		
		imageView.setImageResource(imageIcon);

		return imageView;
	}

}
