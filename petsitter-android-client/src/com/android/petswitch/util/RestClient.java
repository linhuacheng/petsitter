package com.android.petswitch.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class RestClient {

	private boolean authentication;
	private ArrayList<NameValuePair> headers;

	private String jsonBody;
	private String message;

	private ArrayList<NameValuePair> params;
	private String response;
	private int responseCode;

	private String url;

	// HTTP Basic Authentication
	private String username;
	private String password;

	protected Context context;
	private ByteArrayOutputStream byteArrayStream;

	public RestClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public RestClient(SharedPreferences pref, String url) {
		this(url);

		addBasicAuthentication(
				pref.getString(ApplicationConstants.USERNAME, ""),
				pref.getString(ApplicationConstants.PASSWORD, ""));
	}

	// Be warned that this is sent in clear text, don't use basic auth unless
	// you have to.
	public void addBasicAuthentication(String user, String pass) {
		authentication = true;
		username = user;
		password = pass;
	}

	public void addHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void addParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void execute(RequestMethod method) throws Exception {
		switch (method) {
		case GET: {
			HttpGet request = new HttpGet(url + addGetParams());
			Log.i(getClass().getName(), "Path: " + request.getURI().getRawPath());
			Log.i(getClass().getName(), "query: " + request.getURI().getQuery());
			request = (HttpGet) addHeaderParams(request);
			executeRequest(request, url);
			break;
		}
		case POST: {
			HttpPost request = new HttpPost(url);
			request = (HttpPost) addHeaderParams(request);
			request = (HttpPost) addBodyParams(request);
			executeRequest(request, url);
			break;
		}
		case PUT: {
			HttpPut request = new HttpPut(url);
			request = (HttpPut) addHeaderParams(request);
			request = (HttpPut) addBodyParams(request);
			executeRequest(request, url);
			break;
		}
		case DELETE: {
			HttpDelete request = new HttpDelete(url);
			request = (HttpDelete) addHeaderParams(request);
			executeRequest(request, url);
		}
		}
	}

	private HttpUriRequest addHeaderParams(HttpUriRequest request)
			throws Exception {
		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}

		if (authentication) {

			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
					username, password);
			request.addHeader(new BasicScheme().authenticate(creds, request));
		}

		return request;
	}

	private HttpUriRequest addBodyParams(HttpUriRequest request)
			throws Exception {
		if (jsonBody != null) {
			request.addHeader("Content-Type", "application/json");
			if (request instanceof HttpPost)
				((HttpPost) request).setEntity(new StringEntity(jsonBody,
						"UTF-8"));
			else if (request instanceof HttpPut)
				((HttpPut) request).setEntity(new StringEntity(jsonBody,
						"UTF-8"));

		} else if (!params.isEmpty()) {
			if (request instanceof HttpPost)
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(params,
						HTTP.UTF_8));
			else if (request instanceof HttpPut)
				((HttpPut) request).setEntity(new UrlEncodedFormEntity(params,
						HTTP.UTF_8));
		}
		return request;
	}

	private String addGetParams() throws Exception {
		StringBuffer combinedParams = new StringBuffer();
		if (!params.isEmpty()) {
			combinedParams.append("?");
			for (NameValuePair p : params) {
				combinedParams.append((combinedParams.length() > 1 ? "&" : "")
						+ p.getName() + "="
						+ URLEncoder.encode(p.getValue(), "UTF-8"));
			}
		}
		return combinedParams.toString();
	}

	public String getErrorMessage() {
		return message;
	}

	public String getResponse() {
		return response;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setContext(Context ctx) {
		context = ctx;
	}

	public void setJSONString(String data) {
		jsonBody = data;
	}

	private void executeRequest(HttpUriRequest request, String url) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();

		// Setting 30 second timeouts
		HttpConnectionParams.setConnectionTimeout(params, 30 * 1000);
		HttpConnectionParams.setSoTimeout(params, 30 * 1000);

		HttpResponse httpResponse;

		try {
			Log.i(getClass().getName(), "Url:" + url);
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();
			Log.i(getClass().getName(), "message:" + message);
			Log.i(getClass().getName(), "responseCode:" + responseCode);
			HttpEntity entity = httpResponse.getEntity();
			Log.i(getClass().getName(), "entity:" + entity);
			if (entity != null) {

				InputStream instream = entity.getContent();
				Header header = entity.getContentType();
				Log.i(getClass().getName(), "Header" + header);
				Log.i(getClass().getName(), "Header value" + header.getValue());
				if (header.getValue().contains("text/html")
						|| header.getValue().contains(
								"application/json")) {
					response = convertStreamToString(instream);
				} else {

					byteArrayStream = new ByteArrayOutputStream();

					// this is storage overwritten on each iteration with bytes
					int bufferSize = 1024;
					byte[] buffer = new byte[bufferSize];

					// we need to know how may bytes were read to write them to
					// the byteBuffer
					int len = 0;
					while ((len = instream.read(buffer)) != -1) {
						byteArrayStream.write(buffer, 0, len);
					}

					// and then we can return your byte array.
					

					//bitmap = BitmapFactory.decodeStream(instream);

					// Log.i(RestClient.class.getName(), "External storage Dir:"
					// + externalStorageDir.getPath());
					//
					//
					// Log.i(RestClient.class.getName(), path);

				}

				// Closing the input stream will trigger connection release
				instream.close();
			} else {
				Log.i(getClass().getName(),"Response is null"); 
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public ByteArrayOutputStream getByteArrayStream() {
		return byteArrayStream;
	}
}