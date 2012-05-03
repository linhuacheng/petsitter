package com.android.petswitch.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.petswitch.R;
import com.android.petswitch.util.RequestMethod;
import com.android.petswitch.util.RestClient;
import com.android.petswitch.util.RestClientFactory;

/**
 * adatper class used to show image in gallery view
 * 
 * @author ckempaiah
 * 
 */
public class ImageAdapter extends BaseAdapter {
	private Context context;
	private String listingId;
	//private List<String> imageList;
	private SharedPreferences preference;
	int mGalleryItemBackground;
	String remoteFileName;
	private Handler threadHandler;
	private ByteArrayOutputStream byteArrayOutputStream;

	private Integer[] thumbIds = {
	// R.drawable.img1291,
	// R.drawable.img1292,
	// R.drawable.img1293,
	// R.drawable.img1294,
	// R.drawable.img1295,
	// R.drawable.img1296,
	// R.drawable.img1297,
	// R.drawable.img1298

	};

	public ImageAdapter(Context ctx, SharedPreferences prefs,
			String remoteFileName) {
		this.context = ctx;
		this.preference = prefs;
		this.remoteFileName = remoteFileName;
		 TypedArray attr =
		 ctx.obtainStyledAttributes(R.styleable.ImageGalleryView);
		 mGalleryItemBackground = attr.getResourceId(
		 R.styleable.ImageGalleryView_android_galleryItemBackground, 0);
		 attr.recycle();


		// the thread handler for asynchronous fetching of data
		threadHandler = new Handler() {

			public void handleMessage(Message msg) {

				byteArrayOutputStream = (ByteArrayOutputStream) msg.obj;
				if (byteArrayOutputStream == null) {
					Toast.makeText(context,
							"Image was not found on the server",
							Toast.LENGTH_LONG).show();
				}

				notifyDataSetChanged();
				// activity.setProgressBarIndeterminateVisibility(false);
			}
		};

		new DataThread().start();
	}

	// public ImageAdapter(Context ctx, String listingId, List<String>
	// imageLIst){
	// this.listingId = listingId;
	// this.imageList = imageLIst;
	// TypedArray attr =
	// ctx.obtainStyledAttributes(R.styleable.ImageGalleryView);
	// mGalleryItemBackground = attr.getResourceId(
	// R.styleable.ImageGalleryView_android_galleryItemBackground, 0);
	// attr.recycle();
	//
	// this.ctx = ctx;
	//
	// }
	// @Override
	public int getCount() {
		return 1;
	}

	// @Override
	public Object getItem(int position) {
		return null;
	}

	// @Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	// @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView = null;
		if (convertView == null) {
			// construct image view used to render the grid
			imageView = new ImageView(context);
			imageView.setAdjustViewBounds(true);
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 90));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setBackgroundResource(mGalleryItemBackground);
			// imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			// imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			// imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		//ByteArrayOutputStream outputStream = getData();
		if (byteArrayOutputStream != null){
			imageView.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
		}
		// use image downloader to download image

		// imageDownloader.displayImage(listingId, imageList.get(position),
		// imageView);

		return imageView;
	}

	public ByteArrayOutputStream getData() {
		ByteArrayOutputStream outPutStream = null;
		RestClient client = RestClientFactory.getDownloadFileClient(preference);
		client.addParam("fileName", remoteFileName);
		try {
			client.execute(RequestMethod.GET);

			if (client.getResponseCode() != 200) {
				// return server error
				Log.e(getClass().getName(), client.getErrorMessage());
			}
			// return valid data
			outPutStream = client.getByteArrayStream();

		} catch (Exception e) {
			Log.e(getClass().getName(), e.toString());
		}
		return outPutStream;
	}

	class DataThread extends Thread {

		private static final String INNER_TAG = "DataThread";

		public void run() {

			Log.i(INNER_TAG, "Start parsing items");

			RestClient client = RestClientFactory
					.getDownloadFileClient(preference);
			
			client.addParam("fileName", remoteFileName);
			try {
				client.execute(RequestMethod.GET);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
				}
				// return valid data
				byteArrayOutputStream = client
						.getByteArrayStream();

			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			Log.i(INNER_TAG,
					"Done parsing items, send a message to the handler");

			// Send the parsing result to the handler.
			Message dataMsg = threadHandler.obtainMessage();
			dataMsg.obj = byteArrayOutputStream;
			threadHandler.sendMessage(dataMsg);
		}
	}

}
