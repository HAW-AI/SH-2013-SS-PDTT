package de.wpsmarthome.tabpager.utils;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;

import de.wpsmarthome.control.Objects.Blind;
import de.wpsmarthome.control.Objects.Curtain;
import de.wpsmarthome.control.Objects.Light;
import de.wpsmarthome.tabpager.BlindControlFragment;
import de.wpsmarthome.tabpager.ControlFragment;
import de.wpsmarthome.tabpager.CurtainControlFragment;
import de.wpsmarthome.tabpager.KitchenLightControlFragment;
import de.wpsmarthome.tabpager.LightControlFragment;
import de.wpsmarthome.tabpager.utils.Context;
public class ControlFragmentFactory {

	private static Map<Context, Map<Control,SherlockFragment>>  fragmentMap = new HashMap<Context, Map<Control,SherlockFragment>>();
	private static Map<Context, Light> lightMap = new HashMap<Context, Light>();
	private static Map<Context, Blind> blindMap = new HashMap<Context, Blind>();
	private static Map<Context, Curtain> curtainMap = new HashMap<Context, Curtain>();

	static{
		
		for (Context c: Context.values()) {
			fragmentMap.put(c,null);
		}

		lightMap.put(Context.LOUNGE, Light.LOUNGE);
		lightMap.put(Context.DINING, Light.DINING);
		lightMap.put(Context.HALL, Light.CORRIDOR);
		lightMap.put(Context.BEDROOM, Light.SLEEPING);
		
		blindMap.put(Context.LOUNGE, Blind.LOUNGE);
		blindMap.put(Context.KITCHEN, Blind.KITCHEN);
		blindMap.put(Context.DINING, Blind.DINING);
		blindMap.put(Context.BEDROOM, Blind.SLEEPING);
		
		curtainMap.put(Context.LOUNGE, Curtain.LOUNGE);
		curtainMap.put(Context.HALL, Curtain.CORRIDOR);
		curtainMap.put(Context.BEDROOM, Curtain.SLEEPING);
	}
	public static SherlockFragment getInstance(Context context, Control control) {
		if (fragmentMap.get(context) == null){
			fragmentMap.put(context, new HashMap<Control, SherlockFragment>());
		}
		if (fragmentMap.get(context).get(control) == null){
			SherlockFragment fragment;
			Bundle args = new Bundle();
			
			if (context == Context.KITCHEN && control.equals(Control.LIGHT)) {
				fragment = new KitchenLightControlFragment();
			} else if (control.equals(Control.LIGHT) && lightMap.containsKey(context)) {
				fragment = new LightControlFragment();
				args.putSerializable(LightControlFragment.LIGHT, lightMap.get(context));
			} else if (control.equals(Control.BLINDS) && blindMap.containsKey(context)) {
				fragment = new BlindControlFragment();
				args.putSerializable(BlindControlFragment.BLIND, blindMap.get(context));
			} else if (control.equals(Control.CURTAIN) && curtainMap.containsKey(context)) {
				fragment = new CurtainControlFragment();
				args.putSerializable(CurtainControlFragment.CURTAIN, curtainMap.get(context));
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
