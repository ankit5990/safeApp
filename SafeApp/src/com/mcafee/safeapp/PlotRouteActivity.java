package com.mcafee.safeapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PlotRouteActivity extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_route);
		webView = (WebView) findViewById(R.id.webView2);
		    webView.setWebViewClient(new WebViewClient());
		    String url = "http://maps.googleapis.com/maps/api/staticmap?";
		    String style="size=1024x1024&maptype=roadmap";
		    Bundle extras = getIntent().getExtras();
		    if (extras != null) {
		      // String center = "center="+extras.getString("current_location");
		      // url=url+center;
		       url+=style;
		       String markersString=extras.getString("markers");
		       String path = extras.getString("path");
		       url=url+"&"+path+markersString+"&sensor=true";
		    }
		    webView.loadUrl(url);
	    //https://maps.googleapis.com/maps/api/staticmap?size=400x400&format=jpg&sensor=false&path=color%3agreen|weight:5|-21.3322,148.9388|-21.3330,148.9402|-21.3302,148.9397|-21.3287,148.9394|-21.3273,148.9393|-21.3261,148.9392|-21.3247,148.9391|-21.3239,148.9390|-21.3236,148.9389|-21.3236,148.9389|-21.3198,148.9371|-21.3186,148.9364|-21.3175,148.9357|-21.3162,148.9350|-21.3153,148.9347|-21.3141,148.9344|-21.3128,148.9342|-21.3116,148.9341|-21.3103,148.9341|-21.3090,148.9341|-21.3077,148.9340|-21.3064,148.9340|-21.3051,148.9339|-21.3039,148.9339|-21.3026,148.9339|-21.3013,148.9340|-21.3013,148.9340&markers=color%3ared|label%3aSpeed%20violation|-21.3302,148.9397&markers=color%3ablue|label%3aSupport%20Pack%20End|-21.3013,148.9340
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_plot_route, menu);
		return true;
	}

}
