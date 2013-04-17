package de.wpsmarthome.tabpager.utils;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;

public class ContextTabFactory {

	
	public static void createTabs(ActionBar actionBar, Context context, TabListener tabListener) {
		for (Control control : context.getControls()) {
			actionBar.addTab(actionBar.newTab()
						.setText(control.toString())
						.setTabListener(tabListener).setTag(control));
		}
	}

}
