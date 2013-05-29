package de.wpsmarthome.tabpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import de.wpsmarthome.tabpager.R;
import de.wpsmarthome.tabpager.utils.Context;
import de.wpsmarthome.tabpager.utils.ContextDelegate;


public class ControlActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {		
			Bundle b = getIntent().getExtras();
			Context context = (Context)b.getSerializable(ControlFragment.CONTEXT);
			setTitle(context.toString());
			ContextDelegate clfDelegate = new ContextDelegate();
			clfDelegate.onItemSelected(context, this);
		}
	}

	@Override
	public boolean onOptionsItemSelected(
			MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this,
					ContextListActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
