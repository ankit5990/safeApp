package com.mcafee.safeapp.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.mcafee.safeapp.listener.OnPostExecuteListener;

public class AsyncInvokeURLTask extends AsyncTask<Void, Void, String>{
	
	private final String  mWebUrl ;
    private List<NameValuePair> mParams;
    private OnPostExecuteListener mPostExecuteListener = null;
 
    public AsyncInvokeURLTask(String url, List<NameValuePair> nameValuePairs,
        OnPostExecuteListener onPostExecuteListener) throws Exception {
 
        mParams = nameValuePairs;
        mPostExecuteListener = onPostExecuteListener;
        mWebUrl = url;
        if (mPostExecuteListener == null)
            throw new Exception("listener cannot be null.");
    }
 
	@Override
	protected String doInBackground(Void... arg0) {
		 String result = "";
		 
	        // Create a new HttpClient and Post Header
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(mWebUrl);
	 
	        try {
	            // Add parameters
	            httppost.setEntity(new UrlEncodedFormEntity(mParams));
	 
	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            if (entity != null){
	                InputStream inStream = entity.getContent();
	                result = convertStreamToString(inStream);
	            }
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return result;
	}


	private static String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(is));
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
	 @Override
	protected void onPostExecute(String result) {
	        if (mPostExecuteListener != null){
	            try {
	                JSONObject json = new JSONObject(result);
	                mPostExecuteListener.onPostExecute(json);
	            } catch (JSONException e){
	                e.printStackTrace();
	            }
	        }
	    }
}
