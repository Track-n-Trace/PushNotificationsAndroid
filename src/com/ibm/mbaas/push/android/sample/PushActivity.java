//-------------------------------------------------------------------------------
//Copyright 2014 IBM Corp. All Rights Reserved
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0 
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License. 
//-------------------------------------------------------------------------------

package com.ibm.mbaas.push.android.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.push.IBMPush;
import com.ibm.mobile.services.push.IBMPushNotificationListener;
import com.ibm.mobile.services.push.IBMSimplePushNotification;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import bolts.Continuation;
import bolts.Task;


public class PushActivity extends Activity {
	private TextView tv_Welcome = null,tv_Display = null;

	private IBMPush push = null;
	private IBMPushNotificationListener notificationListener = null;
	
	
	private List<String> allTags;
	private List<String> subscribedTags;

    private static final String CLASS_NAME = PushActivity.class.getSimpleName();
    private static final String APP_ID = "applicationID";
    private static final String APP_SECRET = "applicationSecret";
    private static final String APP_ROUTE = "applicationRoute";
    private static final String PROPS_FILE = "bluelist.properties";
	private String username;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PushActivity.this);
		username = sp.getString("username", null);

		tv_Welcome = (TextView) findViewById(R.id.tvWelcome);
		tv_Display = (TextView) findViewById(R.id.tvDisplay);
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tv_Welcome.setText("Welcome "+username);
			}
		});
        // Read from properties file.
        Properties props = new java.util.Properties();
        Context context = getApplicationContext();
        try {
            AssetManager assetManager = context.getAssets();
            props.load(assetManager.open(PROPS_FILE));
            Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
        } catch (FileNotFoundException e) {
            Log.e(CLASS_NAME, "The bluelist.properties file was not found.", e);
        } catch (IOException e) {
            Log.e(CLASS_NAME, "The bluelist.properties file could not be read properly.", e);
        }
        Log.i(CLASS_NAME, "Application ID is: " + props.getProperty(APP_ID));

        // Initialize the IBM core backend-as-a-service.
        IBMBluemix.initialize(this, props.getProperty(APP_ID), props.getProperty(APP_SECRET), props.getProperty(APP_ROUTE));


		
		push = IBMPush.initializeService();
		push.register("UserDevice", sp.getString("username", null)).continueWith(new Continuation<String, Void>() {

			@Override
			public Void then(Task<String> task) throws Exception {

				if (task.isFaulted()) {
					Log.d("reg", "Error registering with Push Service. " + task.getError().getMessage() + "\n"
							+ "Push notifications will not be received.");
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tv_Display.setText("Error in subscription.Login again.");
						}
					});
				} else {
					Log.d("reg", "Device is registered with Push Service" + "\n" + "Device Id : " + task.getResult());
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tv_Display.setText("Subscribed to Notifications.");
						}
					});
				}
				return null;
			}
		});
		

		
		notificationListener = new IBMPushNotificationListener() {

			@Override
			public void onReceive(final IBMSimplePushNotification message) {
				showSimplePushMessage(message);

			}

		};
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (push != null) {
			push.listen(notificationListener);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (push != null) {
			push.hold();
		}
	}



	void showSimplePushMessage(final IBMSimplePushNotification message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Builder builder = new AlertDialog.Builder(PushActivity.this);
				builder.setMessage("Alert: "
						+ message.getAlert());
				builder.setCancelable(true);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int s) {
							}
						});

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {

			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PushActivity.this);
			sp.edit().clear().commit();

			Intent launchactivity = new Intent(PushActivity.this, Login.class);
			startActivity(launchactivity);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}