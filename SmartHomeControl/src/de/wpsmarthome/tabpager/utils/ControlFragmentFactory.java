package de.wpsmarthome.tabpager.utils;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;

import de.wpsmarthome.control.Objects.Light;
import de.wpsmarthome.tabpager.ControlFragment;
import de.wpsmarthome.tabpager.LightControlFragment;
import de.wpsmarthome.tabpager.utils.Context;
public class ControlFragmentFactory {

	private static Map<Context, Map<Control,SherlockFragment>>  fragmentMap = new HashMap<Context, Map<Control,SherlockFragment>>();

	static{
		
		for (Context c: Context.values()) {
			fragmentMap.put(c,null);
		}
	}
	public static SherlockFragment getInstance(Context context, Control control) {
		if (fragmentMap.get(context) == null){
			fragmentMap.put(context, new HashMap<Control, SherlockFragment>());
		}
		if (fragmentMap.get(context).get(control) == null){
			SherlockFragment fragment;
			Bundle args = new Bundle();
			
			if (context == Context.KITCHEN && control.equals(Control.LIGHT)) {
			    fragment = new LightControlFragment();
			    args.putSerializable(LightControlFragment.LIGHT, Light.KITCHEN_MAIN);
			} else {
    		    fragment = new ControlFragment();
			}
			
			args.putSerializable(ControlFragment.CONTEXT, context);
			args.putSerializable(ControlFragment.CONTROL, control);
			fragment.setArguments(args);
			
			fragmentMap.get(context).put(control, fragment);
			
		}
		return fragmentMap.get(context).get(control);
	}

}
