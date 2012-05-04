package com.android.petswitch.util;

import java.io.File;

import android.os.Environment;

public class FileCacheUtil {
	public static final String MEDIA_TYPE_IMAGE = "image";
	public static final String MEDIA_TYPE_VIEDO = "video";
	public static final String MEDIA_TYPE_TEXT = "text";
	public static File getCacheFile(String fileName) {
		
		File externalStorageDir = Environment.getExternalStorageDirectory();
		String path = externalStorageDir.getPath() + "/" + fileName;
		File file = new File(path);
		return file;
	}
	
    public static String getMediaType(String contentType){
    	if (contentType.toLowerCase()
				.matches("img|jpg|png|bmp")) {
			return MEDIA_TYPE_IMAGE;
		} else if (contentType.toLowerCase()
				.matches("mp3|mp4|3gp|avi")){
			return MEDIA_TYPE_VIEDO;
		} else{
			return MEDIA_TYPE_TEXT;
		}
    }
	
}
