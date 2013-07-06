package de.wpsmarthome.tabpager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import de.wpsmarthome.tabpager.utils.Context;
import de.wpsmarthome.tabpager.utils.ContextDelegate;

public class ContextListActivity extends LocationAwareActivity implements
		ContextListFragment.Callbacks {

	private boolean mTwoPane;
	private ContextDelegate clfDelegate = new ContextDelegate();

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

			Log.d(getClass().getSimpleName(), "mTwoPane");
			clfDelegate.onItemSelected(id, this);
		} else {
			Intent detailIntent = new Intent(this, ControlActivity.class);
			detailIntent.putExtra(ControlFragment.CONTEXT, id);
			startActivity(detailIntent);
		}
	}
}