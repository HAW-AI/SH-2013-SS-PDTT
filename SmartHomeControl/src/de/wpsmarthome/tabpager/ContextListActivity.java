package de.wpsmarthome.tabpager;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Contacts.SettingsColumns;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import de.wpsmarthome.tabpager.utils.Context;
import de.wpsmarthome.tabpager.utils.ContextDelegate;
import de.wpsmarthome.ubisense.Constants;
import de.wpsmarthome.ubisense.xmpp.XmppConstants;

public class ContextListActivity extends LocationAwareActivity implements
		ContextListFragment.Callbacks, SettingsDialogFragment.OnIpChangedListener {

	private boolean mTwoPane;
	private ContextDelegate clfDelegate = new ContextDelegate();
	private static final String LOGTAG = ContextListActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_context_list);

		if (findViewById(R.id.control_container) != null) {
			mTwoPane = true;
			((ContextListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.context_list))
					.setActivateOnItemClick(true);
		}

	}

	@Override
	public void onItemSelected(final Context id) {
		if (mTwoPane) {

			Log.d(LOGTAG, "mTwoPane");
			clfDelegate.onItemSelected(id, this);
		} else {
			Intent detailIntent = new Intent(this, ControlActivity.class);
			detailIntent.putExtra(ControlFragment.CONTEXT, id);
			startActivity(detailIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.settings_button:
			showSettingsDialog();
			return true;
		}
		return false;
	}
	
	private void showSettingsDialog() {
		FragmentManager fm = getSupportFragmentManager();
        SettingsDialogFragment settingsDialog = new SettingsDialogFragment();
        settingsDialog.show(fm, "fragment_settings_dialog");
	}

	@Override
	public void storeNewIPAddress(String ipAddress) {
		SharedPreferences sharedPrefs = this.getSharedPreferences(
                XmppConstants.SHARED_PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		if (ipAddress != null) {
			Editor editor = sharedPrefs.edit();
			editor.putString(Constants.XMPP_IP_FROM_SETTINGS_DIALOG, ipAddress);
			editor.commit();
			super.restartService();
		}
	}
}