package de.wpsmarthome.tabpager;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import de.wpsmarthome.tabpager.utils.Context;
import de.wpsmarthome.tabpager.utils.ContextDelegate;
import de.wpsmarthome.ubisense.Constants;
import de.wpsmarthome.ubisense.MessageTranslator;
import de.wpsmarthome.ubisense.xmpp.ServiceManager;
import de.wpsmarthome.ubisense.xmpp.XmppConstants;

public abstract class LocationAwareActivity extends SherlockFragmentActivity {

	protected boolean mTwoPane;
	protected ContextDelegate clfDelegate = new ContextDelegate();
	private ServiceManager serviceManager;
	private BroadcastReceiver notificationReceiver;
	private static final String LOGTAG = LocationAwareActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		serviceManager = new ServiceManager(this);
		serviceManager.startService();
	}

	@Override
	protected void onResume() {
		Log.d(LOGTAG, "onResume");

		notificationReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(android.content.Context context, Intent intent) {
				Log.d(LOGTAG, "NotificationReceiver.onReceive()...");
				String action = intent.getAction();

				if (XmppConstants.ACTION_SHOW_NOTIFICATION.equals(action)) {
					final String notificationMessage = intent
							.getStringExtra(XmppConstants.NOTIFICATION_MESSAGE);

					LocationAwareActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							LocationAwareActivity.this.handleIncomingMessage(notificationMessage);
						}
					});
				}
			}
		};
		Log.d(LOGTAG, "onResume" + " registerReceiver");
		registerReceiver(notificationReceiver, new IntentFilter(
				XmppConstants.ACTION_SHOW_NOTIFICATION));

		super.onResume();
	}

	@Override
	protected void onPause() {
		try {
			Log.d(LOGTAG, "onPause");
			unregisterReceiver(notificationReceiver);			
		} catch (Exception e) {
			Log.e(LOGTAG, "onPause Exception " + e.getMessage());
		}
		super.onPause();			
	}

	@Override
	protected void onDestroy() {
		Log.d(LOGTAG, "onDestroy");
		if (serviceManager != null) {
			serviceManager.stopService();
		}
		super.onDestroy();
	}
	
	protected void onStop() {
		Log.d(LOGTAG, "onStop");
		super.onStop();
	}

	protected void switchToRoom(String room) {
		Log.d(LOGTAG, "switchToRoom..." + room);
		Context context = MessageTranslator.roomNameToContext(room);

		if (context != null) {
			Log.d(LOGTAG, "switching to room " + context);
			if (mTwoPane) {
				Log.d(getClass().getSimpleName(), "mTwoPane");
				clfDelegate.onItemSelected(context, this);
			} else {
				Intent detailIntent = new Intent(this, ControlActivity.class);
				detailIntent.putExtra(ControlFragment.CONTEXT, context);
				startActivity(detailIntent);
			}			
		}
	}

	protected void handleIncomingMessage(String message) {
		try {
			JSONObject serverJSONMessage = new JSONObject(message);
			String room = serverJSONMessage.getString(Constants.OBJECT_ID);
			Log.d(LOGTAG, "handleIncomingMessage " + message);
			Boolean inSpace = Boolean.valueOf(serverJSONMessage
					.getString("InSpace"));
			if (inSpace) {
				LocationAwareActivity.this.switchToRoom(room);
			}
		} catch (JSONException e) {
			Log.e(LOGTAG, "JSONException in handleIncomingMessage");
		}
	}
	
	/**
	 * Needed when the user enters a new XMPP server IP address
	 */
	protected void restartService() {
		if (serviceManager != null) {
			serviceManager.stopService();
			serviceManager = new ServiceManager(this);
			serviceManager.startService();
		}
	}
}
