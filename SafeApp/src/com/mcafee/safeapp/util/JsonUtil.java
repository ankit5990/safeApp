package com.mcafee.safeapp.util;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonUtil {

	public static String parseForMarkersString(String result){
		//output=&markers=color:blue%7Clabel:S%7C40.702147,-74.015794
		//input={"28.6352494,77.22443450000002":"GREEN","28.6361037,77.2235254":"GREEN","28.6351384,77.2216692":"GREEN","28.6371418,77.2238768":"GREEN"}
		String markersString = "";
		if(result!=null && result.length()>0){
			JSONObject jobject;
			try {
				jobject = new JSONObject(result);
				Iterator<String> itr = jobject.keys();
				while(itr.hasNext()){
					markersString+="&markers=";
					String latlong = itr.next();
					String color=(String) jobject.get(latlong);
					markersString+="color:"+color.toLowerCase()+"|"+latlong;
				}
				
			} catch (JSONException e) {
				Log.d("parsingJson", e.getMessage());
				return markersString;
			}
			
		}
		return markersString;
	}
//{"wayPoints":[{"latitude":28.6098833,"longitude":77.229906,"rating":"GRAY","coordinates":{"latitude":28.6098833,"longitude":77.229906}},{"latitude":28.6102134,"longitude":77.23285349999999,"rating":"GRAY","coordinates":{"latitude":28.6102134,"longitude":77.23285349999999}},{"latitude":28.593482,"longitude":77.2436206,"rating":"GRAY","coordinates":{"latitude":28.593482,"longitude":77.2436206}},{"latitude":28.5731313,"longitude":77.2578688,"rating":"GRAY","coordinates":{"latitude":28.5731313,"longitude":77.2578688}},{"latitude":28.5777373,"longitude":77.266134,"rating":"GRAY","coordinates":{"latitude":28.5777373,"longitude":77.266134}},{"latitude":28.5726767,"longitude":77.3098071,"rating":"GRAY","coordinates":{"latitude":28.5726767,"longitude":77.3098071}},{"latitude":28.4495684,"longitude":77.4912634,"rating":"GRAY","coordinates":{"latitude":28.4495684,"longitude":77.4912634}},{"latitude":27.6170189,"longitude":78.2204153,"rating":"GRAY","coordinates":{"latitude":27.6170189,"longitude":78.2204153}}],"
	//completeRatedPath":[{"latitude":28.6098833,"longitude":77.229906,"rating":"GRAY","coordinates":{"latitude":28.6098833,"longitude":77.229906}},{"latitude":28.6102134,"longitude":77.23285349999999,"rating":"GRAY","coordinates":{"latitude":28.6102134,"longitude":77.23285349999999}},{"latitude":28.593482,"longitude":77.2436206,"rating":"GRAY","coordinates":{"latitude":28.593482,"longitude":77.2436206}},{"latitude":28.5731313,"longitude":77.2578688,"rating":"GRAY","coordinates":{"latitude":28.5731313,"longitude":77.2578688}},{"latitude":28.5777373,"longitude":77.266134,"rating":"GRAY","coordinates":{"latitude":28.5777373,"longitude":77.266134}},{"latitude":28.5726767,"longitude":77.3098071,"rating":"GRAY","coordinates":{"latitude":28.5726767,"longitude":77.3098071}},{"latitude":28.4495684,"longitude":77.4912634,"rating":"GRAY","coordinates":{"latitude":28.4495684,"longitude":77.4912634}},{"latitude":27.5332816,"longitude":77.76155349999999,"rating":"GRAY","coordinates":{"latitude":27.5332816,"longitude":77.76155349999999}},{"latitude":27.5366494,"longitude":77.760222,"rating":"GRAY","coordinates":{"latitude":27.5366494,"longitude":77.760222}},{"latitude":27.5769449,"longitude":77.8468596,"rating":"GRAY","coordinates":{"latitude":27.5769449,"longitude":77.8468596}},{"latitude":27.6346492,"longitude":78.1549587,"rating":"GRAY","coordinates":{"latitude":27.6346492,"longitude":78.1549587}},{"latitude":27.5680848,"longitude":78.20934989999999,"rating":"GRAY","coordinates":{"latitude":27.5680848,"longitude":78.20934989999999}},{"latitude":27.5812749,"longitude":78.22123119999999,"rating":"GRAY","coordinates":{"latitude":27.5812749,"longitude":78.22123119999999}},{"latitude":27.6170189,"longitude":78.2204153,"rating":"GRAY","coordinates":{"latitude":27.6170189,"longitude":78.2204153}}]}
	
	public static String parseForPath(String result) {
		//output=path=color:0x0000ff|weight:5|40.737102,-73.990318|40.749825,-73.987963|40.752946,-73.987384|40.755823,-73.986397
		String pathString = "";
		if(result!=null && result.length()>0){
			JSONObject jobject;
			try {
				jobject = new JSONObject(result);
				JSONArray jArray =  (JSONArray) jobject.get("completeRatedPath");
				Log.i("log_tag", jArray.toString()); 
				pathString+="path=color:green|weight:5";
			     for(int i=0;i<jArray.length();i++){
			      JSONObject json_data = jArray.getJSONObject(i);
					String latlong = json_data.getString("latitude")+","+json_data.getString("longitude");
					pathString+="|"+latlong;
			   }
				
			} catch (JSONException e) {
				Log.d("parsingJson", e.getMessage());
				return pathString;
			}
			
		}
		return pathString;
	}

	public static String parseForMarkersStringFromWaypoints(String result) {
		// output =&markers=color:blue%7Clabel:S%7C40.702147,-74.015794
		String markersString = "";
		if(result!=null && result.length()>0){
			JSONObject jobject;
			try {
				jobject = new JSONObject(result);
				JSONArray jArray = (JSONArray) jobject.get("wayPoints");
				Log.i("log_tag", jArray.toString()); 

			     for(int i=0;i<jArray.length();i++){
			      JSONObject json_data = jArray.getJSONObject(i);
			      markersString+="&markers=";
					String latlong = json_data.getString("latitude")+","+json_data.getString("longitude");
					String color=json_data.getString("rating");
					markersString+="color:"+color.toLowerCase()+"|"+latlong;
			   }
				
			} catch (JSONException e) {
				Log.d("parsingJson", e.getMessage());
				return markersString;
			}
			
		}
		return markersString;
	}
}
