package ch.ethz.twimight.activities;

import go.Go;
import go.flashlight.Flashlight;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ch.ethz.twimight.R;

public class ConditionsActivity extends Activity {
	
	static final String TERMS = "termsAccepted";
	static boolean goStarted = false;
	
	private static final String TAG = "Conditions";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Initializing application context. 
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		boolean termsAccepted = settings.getBoolean(TERMS, false);			
		
		if (termsAccepted) {
			startLogin();
			
	    	if (!goStarted) {
	    		// Initializing application context.
	    		Log.d(TAG, "Starting go init");
	    		try {
	    			Log.d(TAG, "Starting flashlight...");
	    			Go.init(getApplicationContext());
	    			Flashlight.RunClientProxy("127.0.0.1:9192");
	    	        System.setProperty("http.proxyHost", "127.0.0.1");
	    	        System.setProperty("http.proxyPort", "9192");
	    		} catch (Exception e) {
	    			Log.e(TAG, "Got exception " + e.getMessage());
	    			throw new RuntimeException(e);
	    		}
	    		goStarted = true;
	    	}
			
		} else {
			
			setContentView(R.layout.show_conditions);		
			Button buttonAgree = (Button)findViewById(R.id.buttonAgree);			
			
			buttonAgree.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ConditionsActivity.this);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean(TERMS, true);
					editor.commit();  
					
					setContentView(R.layout.show_tips);
					Button buttonSkip = (Button)findViewById(R.id.buttonSkip);
					buttonSkip.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							startLogin();
							
						}
						
						
					});
					
				}			
			});
			
		}

		
	}
	
	private void startLogin() {
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	
	

}
