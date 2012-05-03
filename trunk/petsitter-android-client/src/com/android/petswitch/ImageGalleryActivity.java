package com.android.petswitch;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.petswitch.adapter.ImageAdapter;
import com.android.petswitch.util.ApplicationConstants;

/**
 * image gallery activity
 * 
 * @author ckempaiah
 * 
 */
public class ImageGalleryActivity extends Activity {
	public static final String LABEL_PLAY_VIDEO = "Play Video";
	public static final String LABEL_VIEW_VIDEO = "View Videos";
	public static final int STATUS_DOWNLOAD_PROGRESS = 0;
	/** Called when the activity is first created. */
	ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		final String type = (String) extras.get("type");
		final String fileName = (String) extras.get("fileName");
		if (extras != null) {
			if (type.equalsIgnoreCase("image")) {
				setTitle(getString(R.string.viewImages));
				setContentView(R.layout.listinggallerylayout);
			} else {
				setTitle(getString(R.string.viewVideos));
				setContentView(R.layout.listingvideogallerylayout);
			}

			Gallery galleryView = (Gallery) findViewById(R.id.imageGalleryView);

			// extras will contain listing id and type
			final String listingId = (String) extras
					.get("resourceId");
			Log.i(getClass().getSimpleName(), "ListingId:" + listingId);

			// Log.v("Address from extra object:",lds.getAddress());
//			final ListingDetail listingDetail = ((ListingApplication) getApplication())
//					.getListingDetailsById(listingId);

			// Log.v("Address from app object:",listingDetail.getAddress());
			if (type.equalsIgnoreCase("image")) {
				handleImageGallery(galleryView, fileName);
			} else if (type.equalsIgnoreCase("video")) {
				//handleMediaGallery(galleryView, listingDetail);
			} else {
				Toast.makeText(ImageGalleryActivity.this, "Invalid Media type",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * handles image gallery
	 * 
	 * @param galleryView
	 * @param listingDetail
	 */
	private void handleImageGallery(Gallery galleryView,
			final String fileName) {
		SharedPreferences prefs = getSharedPreferences(
				ApplicationConstants.USER_PREF, 0);
		
		galleryView.setAdapter(new ImageAdapter(this, prefs, fileName));

		// handle on item selected listener
		galleryView.setOnItemSelectedListener(new OnItemSelectedListener() {

			//@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				parent.setSelection(0);

			}

			/**
			 * on item selected method
			 */
			//@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				ImageView imageBodyView = (ImageView) findViewById(R.id.imageBodyView);
				//ByteArrayInputStream is = new ByteArrayInputStream(((ImageAdapter)parent.getAdapter()).getData().toByteArray());
				//imageBodyView.setImageBitmap(BitmapFactory.decodeStream(is));
				((ImageAdapter)parent.getAdapter()).getView(0, imageBodyView, parent);
//				new ImageDownloader().displayImage(
//						listingDetail.getListingId(), listingDetail
//								.getImageUrlList().get(position), imageBodyView);
				// Intent showImage = new Intent(getApplicationContext(),
				// ImagePlayer.class);
				// showImage.setData(Uri.parse(lDetail.getImageUrlList().get(0)));

				// startActivity(showImage);
				// Toast.makeText(PhotoViewerActivity.this, "" + position +
				// lDetail.getImageUrlList(), Toast.LENGTH_SHORT).show();
				// Toast.makeText(ImageGridActivity.this, "" + position,
				// Toast.LENGTH_SHORT).show();
			}
		});
	}

//	/**
//	 * handles media gallery
//	 * 
//	 * @param galleryView
//	 * @param listingDetail
//	 */
//	public void handleMediaGallery(final Gallery galleryView,
//			final ListingDetail listingDetail) {
//		final MediaController mediaController = new MediaController(this);
//
//		Button button = (Button) findViewById(R.id.downloadButton);
//		button.setOnClickListener(new OnClickListener() {
//
//			//@Override
//			public void onClick(View v) {
//				if (galleryView.getSelectedItemPosition() >= 0) {
//					String videoUrl = listingDetail.getVideoUrlList().get(
//							galleryView.getSelectedItemPosition());
//					Log.v(getClass().getName(), "Selected video url" + videoUrl);
//					if (!ListingUtil.checkIfCachedFileExists(
//							listingDetail.getListingId(), videoUrl)) {
//						if (URLUtil.isValidUrl(videoUrl)) {
//							new DownloadFileAsync().execute(
//									listingDetail.getListingId(), videoUrl);
//						} else {
//							Toast.makeText(ImageGalleryActivity.this, "Media already on the SD card" , Toast.LENGTH_SHORT).show();
//						}
//					} else {
//						Toast.makeText(ImageGalleryActivity.this, "Media already in cache" , Toast.LENGTH_SHORT).show();
//					}
//
//				}
//
//			}
//		});
//		galleryView.setAdapter(new VideoAdapter(this, listingDetail
//				.getListingId(), listingDetail.getVideoUrlList()));
//
//		// handle on item selected listener
//		galleryView.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//				parent.setSelection(0);
//
//			}
//
//			/**
//			 * on item selected method
//			 */
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View v,
//					int position, long id) {
//
//				mediaController.show(0);
//				VideoView videoView = (VideoView) findViewById(R.id.videoView);
//
//				// mediaController.setAnchorView(videoView);
//				videoView.setMediaController(mediaController);
//
//				// videoView.setVideoURI(Uri.parse("http://hermes.sprc.samsung.pl/widget/tmp/testh.3gp"));
//				// videoView.setVideoPath("/sdcard/ga.3gp");
//				//get the video url
//				String videoUrl = listingDetail.getVideoUrlList().get(position);
//				String path = null;
//
//				Log.v(getClass().getName(), "Selected video url" + videoUrl);
//				
//				if (ListingUtil.checkIfCachedFileExists(
//						listingDetail.getListingId(), videoUrl)) {
//					// set video url to internal link
//					path = ListingUtil.getInternalFilePath(
//							listingDetail.getListingId(), videoUrl);
//				} else {
//					// set video url to external link
//					path = videoUrl;
//				}
//				TextView textView = (TextView)findViewById(R.id.lbMediaName);
//				textView.setText("Selected: " + ListingUtil.getFileName(path));
//				Log.v(getClass().getName(), "Selected path" + path);
//				videoView.setVideoURI(Uri.parse(path));
//				videoView.requestFocus();
//				
//				// videoView.start();
//				
//			}
//		});
//	}
//
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		switch (id) {
//		case STATUS_DOWNLOAD_PROGRESS:
//			progressDialog = new ProgressDialog(this);
//			progressDialog.setMessage("Downloading file..");
//			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			progressDialog.setCancelable(false);
//			progressDialog.show();
//			return progressDialog;
//		default:
//			return null;
//		}
//	}
	/**
	 * innner class to handle asynchronous download with progress report
	 * @author ckempaiah
	 *
	 */
	class DownloadFileAsync extends AsyncTask<String, String, String> {

		

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(STATUS_DOWNLOAD_PROGRESS);
		}
		/**
		 * downlaod media in background reporting status
		 */
		@Override
		protected String doInBackground(String... aurl) {
			int numRead;
			OutputStream os = null;
			InputStream is = null;
			try {

				URL url = new URL(aurl[1]);
				URLConnection con = url.openConnection();
				con.setConnectTimeout(30*1000);
				con.setDoInput(true);
				con.connect();

				int lenghtOfFile = con.getContentLength();
				Log.d("AsyncDownload", "File length: " + lenghtOfFile);

				is = new BufferedInputStream(url.openStream());
				String path = null;//ListingUtil.getInternalFilePath(aurl[0],aurl[1]);
				os = new FileOutputStream(path);

				byte data[] = new byte[1024];

				long total = 0;

				while ((numRead = is.read(data)) != -1) {
					total += numRead;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					os.write(data, 0, numRead);
				}

				os.flush();
			} catch (Exception e) {
				Log.e(getClass().getName(), "Error in downloading media", e);
				
			} finally {
				try {
					if (os != null) {
						os.close();
					}
					
				} catch (Exception ignore) {
					
				}
				try {
					if (is != null) {
						is.close();
					}
					
				} catch (Exception ignore) {
					
				}
			}
			
			return null;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("AsyncDownload", progress[0]);
			progressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(STATUS_DOWNLOAD_PROGRESS);
		}
	}

}
