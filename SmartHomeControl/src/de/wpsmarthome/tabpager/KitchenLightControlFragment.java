package de.wpsmarthome.tabpager;

import de.wpsmarthome.control.Objects.Light;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KitchenLightControlFragment extends ControlFragment {
	private LightControlFragment mAllLightControlFragment;
	private LightControlFragment mMainLightControlFragment;
	private LightControlFragment mCookingLightControlFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_control_kitchen_light, container, false);

		mAllLightControlFragment = createLightControlFragment(Light.KITCHEN_ALL);
		mMainLightControlFragment = createLightControlFragment(Light.KITCHEN_MAIN);
		mCookingLightControlFragment = createLightControlFragment(Light.KITCHEN_COOKING);
		
		// insert fragments into view
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.allLightsFragmentGroup, mAllLightControlFragment);
        fragmentTransaction.add(R.id.mainLightFragmentGroup, mMainLightControlFragment);
        fragmentTransaction.add(R.id.cookingLightFragmentGroup, mCookingLightControlFragment);
        fragmentTransaction.commit();

		return rootView;
	}
	
	private LightControlFragment createLightControlFragment(Light light) {
		Bundle args = new Bundle();
		args.putSerializable(ControlFragment.CONTEXT, mContext);
		args.putSerializable(ControlFragment.CONTROL, mControl);
		args.putSerializable(LightControlFragment.LIGHT, Light.KITCHEN_ALL);
		
		LightControlFragment f = new LightControlFragment();
		f.setArguments(args);
		
		return f;
	}
}
