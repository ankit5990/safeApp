package com.mcafee.safeapp.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

public class SafeAppUtils {

	public static String getRatingFromString(String valueofRating) {
		String rate=valueofRating.toLowerCase();
		if("red".equals(rate)){
		 return "1";
		}
		if("green".equals(rate)){
		 return "3";
		}
		if("yellow".equals(rate)){
			 return "2";
			}
		
		 return "0";
	}

	public static String getTransportFromString(String valueofTransport) {
		if("PEDESTRIAN".equals(valueofTransport)){
			 return "0";
			}
			if("RICKSHAW".equals(valueofTransport)){
			 return "1";
			}
			if("BUS".equals(valueofTransport)){
				 return "2";
				}
			if("CAB".equals(valueofTransport)){
				 return "3";
			}
				return "4";
	}
	
	public static List<Address> getAddressFromLocation(Location location, Context baseContext) throws IOException{
		List<Address> addressList = new ArrayList<Address>();
		    
		    Geocoder gcd = new Geocoder(baseContext,   
		        Locale.getDefault());  
		  try{
			  addressList = gcd.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
		  }catch(Exception ex){
			  Log.e("locationToAddress", "failed to geocode location to address", ex);
		  }
		return addressList;
	}

}
