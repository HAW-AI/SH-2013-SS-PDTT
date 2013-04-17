package de.wpsmarthome.tabpager.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import de.wpsmarthome.tabpager.R;

public class ContextDelegate implements TabListener {

	private ViewPager mPager;
	
	public void onItemSelected(final Context context,
			final SherlockFragmentActivity activity) {

		final ActionBar actionBar = activity.getSupportActionBar();
		
		mPager = (ViewPager) activity.findViewById(R.id.pager);
		mPager.setAdapter(new FragmentStatePagerAdapter(activity
				.getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return context.getControls().length;
			}

			@Override
			public Fragment getItem(int arg0) {
				return ControlFragmentFactory.getInstance(context,
						context.getControls()[arg0]);
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return context.getControls()[position].toString();
			}
		});

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.removeAllTabs();
		ContextTabFactory.createTabs(actionBar, context, this);
		
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
				super.onPageSelected(position);
			}
			
		});
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mPager.setCurrentItem(tab.getPosition(), true);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		//Nothing to do
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// nothing to do
	}

}
