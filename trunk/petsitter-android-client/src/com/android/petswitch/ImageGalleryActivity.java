package com.android.petswitch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.petswitch.adapter.ImageAdapter;
import com.android.petswitch.adapter.VideoAdapter;
import com.android.petswitch.util.ApplicationConstants;
import com.android.petswitch.util.FileCacheUtil;
import com.android.petswitch.util.RequestMethod;
import com.android.petswitch.util.RestClient;
import com.android.petswitch.util.RestClientFactory;

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
			
			// Log.v("Address from app object:",listingDetail.getAddress());
			if (type.equalsIgnoreCase(FileCacheUtil.MEDIA_TYPE_IMAGE)) {
				setTitle(getString(R.string.viewImages));
				setContentView(R.layout.listinggallerylayout);
				Gallery galleryView = (Gallery) findViewById(R.id.imageGalleryView);
				handleImageGallery(galleryView, fileName);
			} else if (type.equalsIgnoreCase(FileCacheUtil.MEDIA_TYPE_VIEDO)) {
				setTitle(getString(R.string.viewVideos));
				setContentView(R.layout.listingvideogallerylayout);
				Gallery galleryView = (Gallery) findViewById(R.id.imageGalleryView);
				handleMediaGallery(galleryView, fileName);
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
	private void handleImageGallery(Gallery galleryView, final String fileName) {
		SharedPreferences prefs = getSharedPreferences(
				ApplicationConstants.USER_PREF, 0);

		galleryView.setAdapter(new ImageAdapter(this, prefs, fileName));

		// handle on item selected listener
		galleryView.setOnItemSelectedListener(new OnItemSelectedListener() {

			// @Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				parent.setSelection(0);

			}

			/**
			 * on item selected method
			 */
			// @Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				ImageView imageBodyView = (ImageView) findViewById(R.id.imageBodyView);
				ImageView selectedAdapterView = (ImageView)v;
				if (selectedAdapterView != null){
					BitmapDrawable drawable = (BitmapDrawable) selectedAdapterView.getDrawable();
					if (drawable != null && drawable.getBitmap() != null) {
						Bitmap bitmap = drawable.getBitmap();
						imageBodyView.setImageBitmap(bitmap);
					}
				}
//				ImageAdapter imageAdapter = ((ImageAdapter) parent.getAdapter());
//				Log.i(getClass().getName(), "Image Adapter:" + imageAdapter);
//				if (imageAdapter != null && imageAdapter.getData() != null){
//					Log.i(getClass().getName(), "Inside impage adapter not null");
//					ByteArrayInputStream is = new ByteArrayInputStream(
//							imageAdapter.getData().toByteArray());
//					imageBodyView.setImageBitmap(BitmapFactory.decodeStream(is));
//				}
				// ((ImageAdapter)parent.getAdapter()).getView(0, imageBodyView,
				// parent);
				// new ImageDownloader().displayImage(
				// listingDetail.getListingId(), listingDetail
				// .getImageUrlList().get(position), imageBodyView);
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

	/**
	 * handles media gallery
	 * 
	 * @param galleryView
	 * @param listingDetail
	 */
	public void handleMediaGallery(final Gallery galleryView,
			final String fileName) {
		galleryView.setAdapter(new VideoAdapter(this, null, Arrays.asList(fileName)));
		final MediaController mediaController = new MediaController(this);

		Button button = (Button) findViewById(R.id.downloadButton);
		button.setOnClickListener(new OnClickListener() {

			// @Override
			public void onClick(View v) {
				if (galleryView.getSelectedItemPosition() >= 0) {

					SharedPreferences prefs = getSharedPreferences(
							ApplicationConstants.USER_PREF, 0);

					Log.v(getClass().getName(), "Selected video url" + fileName);
					if (FileCacheUtil.getCacheFile(fileName).exists()) {

						new DownloadFileAsync().execute(fileName);
					} else {
						Toast.makeText(ImageGalleryActivity.this,
								"Media already in cache", Toast.LENGTH_SHORT)
								.show();
					}

				}

			}
		});
		galleryView.setAdapter(new VideoAdapter(this, null, Arrays.asList(new String[]{fileName})));

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
				String path;
				mediaController.show(0);
				VideoView videoView = (VideoView) findViewById(R.id.videoView);

				// mediaController.setAnchorView(videoView);
				videoView.setMediaController(mediaController);

				// videoView.setVideoURI(Uri.parse("http://hermes.sprc.samsung.pl/widget/tmp/testh.3gp"));
				// videoView.setVideoPath("/sdcard/ga.3gp");
				// get the video url
				Log.v(getClass().getName(), "Selected video url" + fileName);
				File file = FileCacheUtil.getCacheFile(fileName);
				if (file.exists()) {
					// set video url to internal link
					new DownloadFileAsync().execute(fileName);
					path = file.getAbsolutePath();
				} else {
					// set video url to external link
					path = file.getAbsolutePath();
				}
				TextView textView = (TextView) findViewById(R.id.lbMediaName);
				textView.setText("Selected: " + fileName);
				Log.v(getClass().getName(), "Selected path" + path);
				videoView.setVideoURI(Uri.parse(path));
				videoView.requestFocus();
				// videoView.start();

			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case STATUS_DOWNLOAD_PROGRESS:
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Downloading file..");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(false);
			progressDialog.show();
			return progressDialog;
		default:
			return null;
		}
	}

	/**
	 * innner class to handle asynchronous download with progress report
	 * 
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

			InputStream is = null;
			String path;
			String remoteFileName = aurl[0];
			ByteArrayOutputStream byteArrayOutputStream;
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				SharedPreferences preference = getSharedPreferences(
						ApplicationConstants.USER_PREF, 0);
				File file = FileCacheUtil.getCacheFile(remoteFileName);
				if (file.exists()) {

					// compress the bit map
					// bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);

					RestClient client = RestClientFactory
							.getDownloadFileClient(preference);

					client.addParam("fileName", remoteFileName);
					client.execute(RequestMethod.GET);

					if (client.getResponseCode() != 200) {
						// return server error
						Log.e(getClass().getName(), client.getErrorMessage());
					}
					// return valid data
					byteArrayOutputStream = client.getByteArrayStream();
					if (!file.getParentFile().exists()) {
						Log.i(getClass().getName(), "Parent File Path"
								+ file.getParentFile().getPath());
						file.getParentFile().mkdirs();
					}
					// create file out put stream
					fos = new FileOutputStream(file);

					fos.write(byteArrayOutputStream.toByteArray());
					fos.flush();
				} else {
					fis = new FileInputStream(file);
					byte data[] = new byte[1024];
					long total = 0;

					byteArrayOutputStream = new ByteArrayOutputStream();
					while ((numRead = fis.read(data)) != -1) {
						total += numRead;

						byteArrayOutputStream.write(data, 0, numRead);
					}
				}
				
			} catch (Exception e) {
				Log.e(getClass().getName(), "Error in downloading media", e);

			} finally {
				try {
					if (fos != null) {
						fos.close();
					}

				} catch (Exception ignore) {

				}
				try {
					if (fis != null) {
						fis.close();
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
