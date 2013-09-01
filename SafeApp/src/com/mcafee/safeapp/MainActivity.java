package com.mcafee.safeapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mcafee.safeapp.http.AsyncInvokeURLTask;
import com.mcafee.safeapp.listener.OnPostExecuteListener;
import com.mcafee.safeapp.util.JsonUtil;
import com.mcafee.safeapp.util.SafeAppUtils;

public class MainActivity extends Activity implements OnClickListener{
	private Button ratingButton;
	private Button sosButton;
	private Button findSafeRouteButton;
	private LocationManager locationManager;
	private LocationListener gpsLocationListener;
	private LocationListener networkLocationListener;
	private Location gpslocation = null;
	private Location networkLocation = null;
	private Button getRatingButton;
	String ip="192.168.2.3:8080";
	private Dialog locationDetailsDialog =null;
	   private Dialog savingDialog;
	   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ratingButton = (Button) findViewById(R.id.rating_button);
		sosButton = (Button) findViewById(R.id.sos_button);
		findSafeRouteButton = (Button) findViewById(R.id.findRoute_button);
		getRatingButton = (Button) findViewById(R.id.getRating_button);
		
		if(locationManager==null){
	          locationManager = (LocationManager) getApplicationContext() .getSystemService(Context.LOCATION_SERVICE);
	       }
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||(Settings.Secure.getString(getContentResolver(),
			  Settings.Secure.LOCATION_PROVIDERS_ALLOWED)=="")){
		showSettingsForProviders("Location Services not available!", "Please activate GPS and Location Services on your phone to get full advantage of this app.Without GPS and Location Services, you will not be able to record position and navigate.\nWould you like to enable Location Services now?");
		}
		initializeLocationListeners();
		startListeningForlocation();

		ratingButton.setOnClickListener(this);
		sosButton.setOnClickListener(this);
		getRatingButton.setOnClickListener(this);
		findSafeRouteButton.setOnClickListener(this);
	}

	private void initializeLocationListeners() {
    	gpsLocationListener = new GPSLocationListener();
    	networkLocationListener = new NetworkLocationListener();
		
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.findRoute_button){
			Location location = searchForLocation();
			if(location!=null){
			Log.d("location", String.valueOf(location.getLatitude()));
			
			String url = "http://"+ip+"/SafetyWebApp/getRatedPath.do";
			Date now = new Date();
			final String latitude = String.valueOf(location.getLatitude());
			final String longitude = String.valueOf(location.getLongitude());
			String destLat="28.4467637";
			String destLong="77.0608787";
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			String timeMillies = String.valueOf(now.getTime());
			url=url+"?start="+latitude+","+longitude+"&end="+destLat+","+destLong+"&timeMillies="+timeMillies+"&transport=2";
			//1,12345656&transport=3&radius=2
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			OnPostExecuteListener listener = new OnPostExecuteListener() {
		        public void onPostExecute(String result) {
		            Log.d("getRatedPath", "recieved result");
		            
		            String markers =JsonUtil.parseForMarkersStringFromWaypoints(result);
		            String path = JsonUtil.parseForPath(result);
		            if(result!=null && result.length()>0){
		            	Intent i=new Intent(MainActivity.this,
		            			PlotRouteActivity.class);
		            	i.putExtra("path", path);
		            	i.putExtra("markers", markers);
		            	startActivity(i);
		            	}
		        }
		    };
			
			try {
				AsyncInvokeURLTask task = new AsyncInvokeURLTask(url, nameValuePairs, listener );
				task.execute();
			} catch (Exception e) {
				Log.e("saveRating", "error occured..unable to save rating",e);
			}
			}
		}else
		if(v.getId() == R.id.getRating_button){
			final Location location = searchForLocation();
			if(location!=null){
				LayoutInflater factory = LayoutInflater.from(this);
				final View dialogView = factory.inflate(R.layout.custom_transport_dialog,null);
				locationDetailsDialog = new Dialog(this);
				locationDetailsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
				locationDetailsDialog.setContentView(dialogView);
				Button saveLocationDetails = (Button) dialogView.findViewById(R.id.getRating_button);
				TextView locationCommentsLabel = (TextView) dialogView.findViewById(R.id.selectTrnsportText);
				locationCommentsLabel.setText("Please select your preferred mode of transport and get rating for your current location for this time of day.");
				
				final Spinner spiiner_transport=  (Spinner) dialogView.findViewById(R.id.spinner3);
				
				saveLocationDetails.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
				         String valueoftransport = String.valueOf(spiiner_transport.getSelectedItem()); 
				         String transport= SafeAppUtils.getTransportFromString(valueoftransport);
				         String url = "http://"+ip+"/SafetyWebApp/getRatingMapAroundCoordinate.do";
							Date now = new Date();
							final String latitude = String.valueOf(location.getLatitude());
							final String longitude = String.valueOf(location.getLongitude());
							Calendar cal = Calendar.getInstance();
							cal.setTime(now);
							String timeMillies = String.valueOf(now.getTime());
							url=url+"?coordinateString="+latitude+","+longitude+"&timeMillies="+timeMillies+"&transport="+transport+"&radius=90000000";
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
							OnPostExecuteListener listener = new OnPostExecuteListener() {
						        public void onPostExecute(String result) {
						            Log.d("getRating", "recieved result");
						            if(savingDialog!=null&& savingDialog.isShowing()){
										savingDialog.dismiss();
									}
						            String markers =JsonUtil.parseForMarkersString(result);
						            if(result!=null && result.length()>0){
						            	Intent i=new Intent(MainActivity.this,
						            			WebViewActivity.class);
						            	i.putExtra("current_location", latitude+","+longitude);
						            	i.putExtra("markers", markers);
						            	startActivity(i);
						            	}else{
						            		showLocationSavedDialog(false, "Unable to get Rating!", "Please check your network connection and try again");
						            	}
						        }
						    };
							
							try {
								AsyncInvokeURLTask task = new AsyncInvokeURLTask(url, nameValuePairs, listener );
								task.execute();
							} catch (Exception e) {
								Log.e("saveRating", "error occured..unable to get rating",e);
							}
				     savingDialog = createSavingLocationDialog("Fetching rating");
				     savingDialog.show();
				     locationDetailsDialog.dismiss();
					}
				});
				
				Log.d("fethc rating", "before show");
				locationDetailsDialog.show();
			
		}else{
			Log.d("fetch rating", "null location, not fetching..");
		}
	}else
		if(v.getId() == R.id.rating_button){
			final Location location = searchForLocation();
			if(location!=null){
				LayoutInflater factory = LayoutInflater.from(this);
				final View dialogView = factory.inflate(R.layout.custom_save_dialog,null);
				locationDetailsDialog = new Dialog(this);
				locationDetailsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
				locationDetailsDialog.setContentView(dialogView);
				Button saveLocationDetails = (Button) dialogView.findViewById(R.id.saveLocationDetails_button);
				TextView locationCommentsLabel = (TextView) dialogView.findViewById(R.id.locationDetailsText);
				locationCommentsLabel.setText("Please select your mode of transport and rate your current location for this time of day.");
				
				final Spinner spiiner_rating=  (Spinner) dialogView.findViewById(R.id.spinner1);
				final Spinner spiiner_transport=  (Spinner) dialogView.findViewById(R.id.spinner2);
				
				saveLocationDetails.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
				          String valueofRating = String.valueOf(spiiner_rating.getSelectedItem()); 
				         String rating= SafeAppUtils.getRatingFromString(valueofRating);
				         String valueoftransport = String.valueOf(spiiner_transport.getSelectedItem()); 
				         String transport= SafeAppUtils.getTransportFromString(valueoftransport);
				          String url = "http://"+ip+"/SafetyWebApp/saveRating.do";
							Date now = new Date();
							String latitude = String.valueOf(location.getLatitude());
							String longitude = String.valueOf(location.getLongitude());
							Calendar cal = Calendar.getInstance();
							cal.setTime(now);
							String timeMillies = String.valueOf(now.getTime());
							url=url+"?coordinateString="+latitude+","+longitude+"&timeMillies="+timeMillies+"&transport="+transport+"&rating="+rating;
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
							OnPostExecuteListener listener = new OnPostExecuteListener() {
						        public void onPostExecute(String result) {
						            Log.d("saveRating", "rating saved");
						            if(savingDialog!=null&& savingDialog.isShowing()){
										savingDialog.dismiss();
									}
						            if(result!=null && result.contains("1")){
						            	showLocationSavedDialog(true,"Rating Saved","Thank you! Your rating for current location has been saved successfully.\n");
						            }else{
						            	Log.d("saveRating", "rating not saved");
						            	 showLocationSavedDialog(false,"Rating not saved","Sorry!Your rating for current location cannot be saved.\nPlease check that you are conected to the network and try again.\n");
						            }
						        }
						    };
							
							try {
								AsyncInvokeURLTask task = new AsyncInvokeURLTask(url, nameValuePairs, listener );
								task.execute();
							} catch (Exception e) {
								Log.e("saveRating", "error occured..unable to save rating",e);
								Toast.makeText(getBaseContext(), "Sorry! Unable to save. Please try again.", 
				                        Toast.LENGTH_SHORT).show();
							}
				     savingDialog = createSavingLocationDialog("Saving rating");
				     savingDialog.show();
				     locationDetailsDialog.dismiss();
					}
				});
				
				Log.d("save loc", "before show");
				locationDetailsDialog.show();
			
		}else{
			Log.d("save location", "null location, not saving..");
		}
	}else if(v.getId() == R.id.sos_button){
				Location location = searchForLocation();
				if(location!=null){
				Log.d("location", String.valueOf(location.getLatitude()));
				}
				Date now = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				String smsSubject = "Urgent!Help! I am in trouble!";
				String smsText=smsSubject+"\n";
				if(location!=null){
				smsText+="My location at"+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+" was\n"+
				"http://maps.google.com/maps?q="+location.getLatitude()+","+location.getLongitude();
				smsText+="\n (Accuracy:"+location.getAccuracy()+" m)";
			}
				sendSMS("09899860559", smsText);
				sendSMS("09868937474", smsText);
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:09868937474"));
				startActivity(intent);
			}
	}
	
	protected Dialog createSavingLocationDialog(String title) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle(title);
	    	builder.setMessage("Please wait..");
	    	return  builder.create();
		}

	protected void showLocationSavedDialog(boolean b, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	StringBuffer messageb = new StringBuffer();
    			
    	builder.setTitle(title);
    	messageb.append(message);
    	builder.setMessage(messageb.toString());
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	@Override
    	public void onClick(DialogInterface dialog, int which) {
    	 dialog.dismiss();
    	}
    });
    	
     builder.create().show();
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
	@Override
	  protected void onPause() {
	     if(locationManager!=null){
		  locationManager.removeUpdates(networkLocationListener);
		  locationManager.removeUpdates(gpsLocationListener);
	     }
	    super.onPause();
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
