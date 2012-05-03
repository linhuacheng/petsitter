package com.android.petswitch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
/**
 * image downloader class used to download a image and store it on sd card
 * 
 * @author ckempaiah
 *
 */
public class ImageDownloader {
	public static final int HTTP_CONNECTION_TIMEOUT = 4000;	
	/**
	 * get bitmap object for given listingid and url
	 * 
	 * @param listingId
	 * @param strURL
	 * @return
	 */
	public Bitmap getImageBitMap(String listingId, String strURL){
		
		HttpURLConnection urlConnection = null;
		FileOutputStream fos  = null;
		InputStream is = null;
		String path = null;
		Bitmap bitmap = null;
		try {
			//get external storage directory
			File externalStorageDir = Environment.getExternalStorageDirectory();
			
			String fileName = strURL.substring(strURL.lastIndexOf("/")+1);
			path = externalStorageDir.getPath() + "/" + listingId + "/" + fileName;
			File file = new File(path);
			
			//
			if (!file.exists()) {
				//checks and downloads  image only if it doesn't exist on the external disk 
				URL url = new URL(strURL);
				urlConnection = (HttpURLConnection)url.openConnection();
				//set connection timeout 
				urlConnection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT);
				urlConnection.setDoInput(true);
				urlConnection.connect();
	            is = urlConnection.getInputStream();
	            bitmap = BitmapFactory.decodeStream(is);
	            
	            Log.i(ImageDownloader.class.getName(), "External storage Dir:" + externalStorageDir.getPath());
	            
	            
	            Log.i(ImageDownloader.class.getName(), path);
	            
	            
	            if (!file.getParentFile().exists()){
	            	Log.i(ImageDownloader.class.getName(), "Parent File Path" + file.getParentFile().getPath());
	            	file.getParentFile().mkdirs();
	            }
	            //create file out put stream
	            fos = new FileOutputStream(file);
	            //compress the bit map
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
	            fos.flush();
			} else {
				bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(path)));
			}
            
		} catch (Exception e) {
			Log.e(getClass().getName(), "Error in image downloader", e);
		} finally {
			//release io connections
			if(is != null) {
				try {
					is.close();
				} catch (Exception e2) {
					Log.e(getClass().getName(), "Exception", e2);
				}
			}
			if (fos != null){
				try {
					fos.close();
				} catch (Exception e2) {
					Log.e(getClass().getName(), "Exception", e2);
				}
			}
			
		}
		return bitmap;
		
	}
	/**
	 * downloads sets bitmap image in to image view.
	 * @param listingId
	 * @param strUrl
	 * @param imageView
	 */
	public void displayImage(String listingId, String strUrl, ImageView imageView){
		imageView.setImageBitmap(getImageBitMap(listingId, strUrl));
		//imageView.setImageDrawable(loadDwawable(strUrl));
	}
	
	
}
