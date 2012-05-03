package com.android.petswitch;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import com.android.petswitch.adapter.RequestPickerAdapter;
import com.android.petswitch.dto.RequestResponseDetail;
import com.android.petswitch.util.ApplicationConstants;
import com.android.petswitch.util.CustomMultiPartEntity;
import com.android.petswitch.util.CustomMultiPartEntity.ProgressListener;

public class CameraPreview extends Activity {

	private static String TAG = "ShareMedia";

	private static final int ACTION_TAKE_PHOTO_B = 1;
	private static final int ACTION_TAKE_PHOTO_S = 2;
	private static final int ACTION_TAKE_VIDEO = 3;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	private ImageView mImageView;
	private Bitmap mImageBitmap;

	private static final String VIDEO_STORAGE_KEY = "viewvideo";
	private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";
	private VideoView mVideoView;
	private Uri mVideoUri;

	private String mCurrentPhotoPath;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	
	private static final String VID_FILE_PREFIX = "VID_";
	private static final String VID_FILE_SUFFIX = ".3gp";

	private String selectedPath = "";

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

	private SharedPreferences mPrefs;
	
	private RequestResponseDetail mRequestResponseDetail;
	private EditText mComment;
	
	private  RequestPickerAdapter adapter;

	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			storageDir = mAlbumStorageDirFactory
					.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
				albumF);
		return imageF;
	}

	private File createVideoFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = VID_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName,VID_FILE_SUFFIX, albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {

		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();

		return f;
	}

	private File setUpVideoFile() throws IOException {

		File f = createVideoFile();
		mCurrentPhotoPath = f.getAbsolutePath();

		return f;
	}

	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
		mImageView.setImageBitmap(bitmap);
		mVideoUri = null;
		mImageView.setVisibility(View.VISIBLE);
		mVideoView.setVisibility(View.INVISIBLE);
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		switch (actionCode) {
		case ACTION_TAKE_PHOTO_S:
		case ACTION_TAKE_PHOTO_B:
			File f = null;

			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
			break;

		default:
			break;
		} // switch

		startActivityForResult(takePictureIntent, actionCode);
	}

	private void dispatchTakeVideoIntent() {
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		File f;
		try {
			f = setUpVideoFile();

			mCurrentPhotoPath = f.getAbsolutePath();
			takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
	}

	private void dispatchShareFileIntent() {

		// List items

		// Prepare the list dialog box
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// Set its title
		builder.setTitle("Share to...");

		 adapter = new RequestPickerAdapter(this, mPrefs);

		// Set the list items and assign with the click listener
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			// Click listener
			public void onClick(DialogInterface dialog, int item) {
				mRequestResponseDetail = (RequestResponseDetail) adapter.getItem(item);
				
				new HttpMultipartPost().execute();
				
			}
		});

		AlertDialog alert = builder.create();

		alert.show();
	}

	private void handleSmallCameraPhoto(Intent intent) {
		Log.i(TAG, "Photo path: " + mCurrentPhotoPath);
		mImageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

		mImageView.setImageBitmap(mImageBitmap);
		mVideoUri = null;
		mImageView.setVisibility(View.VISIBLE);
		mVideoView.setVisibility(View.INVISIBLE);
	}

	private void handleBigCameraPhoto() {

		if (mCurrentPhotoPath != null) {
			setPic();
			galleryAddPic();
			mCurrentPhotoPath = null;
		}

	}

	private void handleCameraVideo(Intent intent) {
		mVideoUri = intent.getData();
		mVideoView.setVideoURI(mVideoUri);
		mImageBitmap = null;
		mVideoView.setVisibility(View.VISIBLE);
		mImageView.setVisibility(View.INVISIBLE);
	}

	ImageButton.OnClickListener mTakePicOnClickListener = new ImageButton.OnClickListener() {
		public void onClick(View v) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
		}
	};

	ImageButton.OnClickListener mTakePicSOnClickListener = new ImageButton.OnClickListener() {
		public void onClick(View v) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
		}
	};

	ImageButton.OnClickListener mTakeVidOnClickListener = new ImageButton.OnClickListener() {
		public void onClick(View v) {
			dispatchTakeVideoIntent();
		}
	};

	ImageButton.OnClickListener mShareFileOnClickListener = new ImageButton.OnClickListener() {
		public void onClick(View v) {
			dispatchShareFileIntent();
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_media);

		mImageView = (ImageView) findViewById(R.id.imageView1);
		mVideoView = (VideoView) findViewById(R.id.videoView1);
		mImageBitmap = null;
		mVideoUri = null;

		mComment = (EditText) findViewById(R.id.comment_text);
		
		mPrefs = getSharedPreferences(ApplicationConstants.USER_PREF, 0);

		// Button picBtn = (Button) findViewById(R.id.btnIntend);
		// setBtnListenerOrDisable(picBtn, mTakePicOnClickListener,
		// MediaStore.ACTION_IMAGE_CAPTURE);

		Button picSBtn = (Button) findViewById(R.id.btnIntendS);
		setBtnListenerOrDisable(picSBtn, mTakePicSOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE);

		Button vidBtn = (Button) findViewById(R.id.btnIntendV);
		setBtnListenerOrDisable(vidBtn, mTakeVidOnClickListener,
				MediaStore.ACTION_VIDEO_CAPTURE);

		Button shareBtn = (Button) findViewById(R.id.btnIntendShare);
		shareBtn.setOnClickListener(mShareFileOnClickListener);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// case ACTION_TAKE_PHOTO_B: {
		// if (resultCode == RESULT_OK) {
		// handleBigCameraPhoto();
		// }
		// break;
		// } // ACTION_TAKE_PHOTO_B

		case ACTION_TAKE_PHOTO_S: {
			if (resultCode == RESULT_OK) {
				handleSmallCameraPhoto(data);
			}
			break;
		} // ACTION_TAKE_PHOTO_S

		case ACTION_TAKE_VIDEO: {
			if (resultCode == RESULT_OK) {
				handleCameraVideo(data);
			}
			break;
		} // ACTION_TAKE_VIDEO
		} // switch
	}

	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		outState.putParcelable(VIDEO_STORAGE_KEY, mVideoUri);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY,
				(mImageBitmap != null));
		outState.putBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY,
				(mVideoUri != null));
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		mVideoUri = savedInstanceState.getParcelable(VIDEO_STORAGE_KEY);
		mImageView.setImageBitmap(mImageBitmap);
		mImageView
				.setVisibility(savedInstanceState
						.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? ImageView.VISIBLE
						: ImageView.INVISIBLE);
		mVideoView.setVideoURI(mVideoUri);
		mVideoView
				.setVisibility(savedInstanceState
						.getBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY) ? ImageView.VISIBLE
						: ImageView.INVISIBLE);
	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 * 
	 * @param context
	 *            The application's environment.
	 * @param action
	 *            The Intent action to check for availability.
	 * 
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private void setBtnListenerOrDisable(Button btn,
			Button.OnClickListener onClickListener, String intentName) {
		if (isIntentAvailable(this, intentName)) {
			btn.setOnClickListener(onClickListener);
		} else {
			// btn.setText(getText(R.string.cannot).toString() + " "
			// + btn.getText());
			btn.setClickable(false);
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private ProgressDialog mProgressDialog;
	public static final int DIALOG_UPLOAD_PROGRESS = 0;

	class HttpMultipartPost extends AsyncTask<String, Integer, String> {
		// ProgressDialog pd;
		long totalSize;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(DIALOG_UPLOAD_PROGRESS);

		}

		@Override
		protected String doInBackground(String... arg0)
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(ApplicationConstants.UPLOAD_FILE_URL);
 
			try
			{
				
				UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
						mPrefs.getString(ApplicationConstants.USERNAME, ""), mPrefs.getString(ApplicationConstants.PASSWORD, ""));
				httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost));
						
				CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(new ProgressListener()
				{
					public void transferred(long num)
					{
						publishProgress((int) ((num / (float) totalSize) * 100));
					}
				});
 
				 Uri fileUri = null; // if there is an image, upload the image
				 if (mImageBitmap != null) { 
					 fileUri = Uri.parse(mCurrentPhotoPath);
				     //Toast.makeText(getApplicationContext(), mCurrentPhotoPath,Toast.LENGTH_SHORT).show();
				     // else upload the video 
				 } else {
					 fileUri = mVideoUri; 
					// Toast.makeText(getApplicationContext(), mVideoUri.getPath(), Toast.LENGTH_SHORT).show(); 
				 } 
				 Log.i(TAG, "File path: " + fileUri.getPath());
				// We use FileBody to transfer an image
				multipartContent.addPart("requestId", new StringBody(mRequestResponseDetail.getRequestId().toString()));
				multipartContent.addPart("fileName", new StringBody(""));
				multipartContent.addPart("comment", new StringBody(mComment.getText().toString()));
				multipartContent.addPart("file", new FileBody(new File(fileUri.getPath())));
				totalSize = multipartContent.getContentLength();
 
				Log.i(TAG, "Posting file");
				// Send it
				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost, httpContext);
				String serverResponse = EntityUtils.toString(response.getEntity());

				Log.i(TAG, serverResponse);
				return null;
			}
 
			catch (Exception e)
			{
				System.out.println(e);
				Log.e(TAG, e.toString(), e);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			mProgressDialog.setProgress((int) (progress[0]));
		}

		@Override
		protected void onPostExecute(String ui) {
			
			try {
				// Get reference to the SmsManager that manages SMS operations such as sending text messages.
				SmsManager smsManager = SmsManager.getDefault();
				// send the text message passing the phone number and message
				smsManager.sendTextMessage(mRequestResponseDetail.getRequesterPhoneNumber(), null, mComment.getText().toString(), null, null);
				mComment.setText("");
			  } catch (Exception e) {
				Log.e(TAG, "Error sending SMS", e);
				e.printStackTrace();
			  }
			dismissDialog(DIALOG_UPLOAD_PROGRESS);
			// pd.dismiss();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_UPLOAD_PROGRESS: // we set this to 0
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setMessage("Uploading file...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}
}