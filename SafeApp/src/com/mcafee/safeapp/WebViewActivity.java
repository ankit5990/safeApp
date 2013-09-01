package com.mcafee.safeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		webView = (WebView) findViewById(R.id.webView1);
	   // webView.getSettings().setJavaScriptEnabled(true);
	    webView.setWebViewClient(new WebViewClient());
	    String url = "http://maps.googleapis.com/maps/api/staticmap?";
	    String style="size=1024x1024&maptype=roadmap";
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	      // String center = "center="+extras.getString("current_location");
	      // url=url+center;
	       url+=style;
	       String markersString=extras.getString("markers");
	       url=url+markersString+"&sensor=true";
	    }
	    webView.loadUrl(url);
	    //"http://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=14&size=512x512&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=false
	    		
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_view, menu);
		return true;
	}

}
