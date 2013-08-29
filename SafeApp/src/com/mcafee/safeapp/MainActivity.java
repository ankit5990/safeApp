package com.mcafee.safeapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mcafee.safeapp.http.AsyncInvokeURLTask;
import com.mcafee.safeapp.listener.OnPostExecuteListener;

public class MainActivity extends Activity implements OnClickListener{
	private Button ratingButton;
	private Button sosButton;
	private LocationManager locationManager;
	private LocationListener gpsLocationListener;
	private LocationListener networkLocationListener;
	private Location gpslocation = null;
	private Location networkLocation = null;
	   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ratingButton = (Button) findViewById(R.id.rating_button);
		sosButton = (Button) findViewById(R.id.sos_button);
		
		if(locationManager==null){
	          locationManager = (LocationManager) getApplicationContext() .getSystemService(Context.LOCATION_SERVICE);
	       }
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||(Settings.Secure.getString(getContentResolver(),
			  Settings.Secure.LOCATION_PROVIDERS_ALLOWED)=="")){
		showSettingsForProviders("Location Services not available!", "Please activate GPS and Location Services on your phone to get full advantage of this app.Without GPS and Location Services, you will not be able to record position and navigate.\nWould you like to enable Location Services now?");
		}
		initializeLocationListeners();
		
		ratingButton.setOnClickListener(this);
		sosButton.setOnClickListener(this);
	}

	private void initializeLocationListeners() {
    	gpsLocationListener = new GPSLocationListener();
    	networkLocationListener = new NetworkLocationListener();
		
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.rating_button){
			Location location = searchForLocation();
			if(location!=null){
			Log.d("location", String.valueOf(location.getLatitude()));
			}
			String url = "http://localhost:8080/SafeWebApp/saveRating.do";
			Date now = new Date();
			String latitude = String.valueOf(location.getLatitude());
			String longitude = String.valueOf(location.getLongitude());
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("coordinateString", latitude+":"+longitude));
			nameValuePairs.add(new BasicNameValuePair("timeMillies", String.valueOf(now.getTime())));
			nameValuePairs.add(new BasicNameValuePair("transport", "Bus"));
			nameValuePairs.add(new BasicNameValuePair("rating", "3"));
			OnPostExecuteListener listener = new OnPostExecuteListener() {
		        public void onPostExecute(JSONObject result) {
		            Log.d("saveRating", "rating saved");
		        }
		    };
			
			try {
				AsyncInvokeURLTask task = new AsyncInvokeURLTask(url, nameValuePairs, listener );
				task.execute();
			} catch (Exception e) {
				Log.e("saveRating", "error occured..unable to save rating",e);
			}
			//https://maps.google.com/maps?saddr=25.04202,121.534761&daddr=25.05202,121.554761%20%28name%29%20to:25.06202,121.554761%20to:%2025.07202,121.554761
			//https://maps.google.com/maps?q=25.05202,121.554761%20%28name%29%20to:25.06202,121.554761%20%28name%29%20to:25.07202,121.554761%20%28name%29
			}else if(v.getId() == R.id.sos_button){
				Location location = searchForLocation();
				if(location!=null){
				Log.d("location", String.valueOf(location.getLatitude()));
				}
				Date now = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				String smsSubject = "Urgent!Help! I am in trouble!";
				String smsText=smsSubject+"/n";
				if(location!=null){
				smsText+="My location at"+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":\n"+
				"http://maps.google.com/maps?q="+location.getLatitude()+","+location.getLongitude();
				smsText+="\n (Accuracy:"+location.getAccuracy()+" m)";
			}
				sendSMS("09899860559", smsText);
				sendSMS("09868937474", smsText);
			}
		
		 
		
	}
	
	private Location searchForLocation() {
		startListeningForlocation();
		Location location = getLocation();
		if(location == null){
			if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||(Settings.Secure.getString(getContentResolver(),
			  			  Settings.Secure.LOCATION_PROVIDERS_ALLOWED)=="")){
					showSettingsForProviders("No location found yet!", "Please enable GPS and Location Services on your phone to quickly fetch location and try again.");
			}
			else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle("Please wait..");
	    	builder.setMessage("No location found yet.\nPlease move a few meters to get a better reception and try again.");
	    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    	@Override
	    	public void onClick(DialogInterface dialog, int which) {
	    			      dialog.dismiss();
	    	 
	    	}
	    });
	    	
	     builder.create().show();
			}
		}		
		return location;
	}
	
	private Location getLocation() {            
        if(gpslocation==null && networkLocation==null){
            Log.d("getlocation", "no last known location found..rturning null");
            return null;
        }

        if(gpslocation!=null && networkLocation!=null){
            if(gpslocation.getTime() < networkLocation.getTime()){
                gpslocation = null;
                Log.d("getlocation", "rturning network location");
                return networkLocation;
            }else{
                networkLocation = null;
                Log.d("getlocation", "rturning gpslocation");
                return gpslocation;
            }
        }
        if (gpslocation == null) {
        	Log.d("getlocation", "rturning network location");
        	return networkLocation;
        }
        if (networkLocation == null) {
        	 Log.d("getlocation", "rturning gpslocation");
        	return gpslocation;
        }
        return null;
    }
	
	private void startListeningForlocation() {
		try{
	        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	        	
	        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, gpsLocationListener);
	            }
	        
	            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
	                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, networkLocationListener);
	            }
	        } catch (IllegalArgumentException e) {
	            Log.e("error", e.toString());
	        }
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void showSettingsForProviders(String title, String message) {
    	//Ask the user to enable GPS
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	@Override
    	public void onClick(DialogInterface dialog, int which) {
    			        	            //Launch settings, allowing user to make a change
    	 Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    	 startActivity(i);
    	}
    });
    	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
    	 @Override
    	 public void onClick(DialogInterface dialog, int which) {
    	 //No location service, no Activity
    	 dialog.cancel();
    	}
    });
     builder.create().show();
		
	}
	/*
	 * Location listeners for gps, network and passive
	 */
	private class GPSLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			gpslocation = location;
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	private class NetworkLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			networkLocation = location;
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void sendSMS(String phoneNumber, String message)
    {        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));        

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        
    }

}
