package de.wpsmarthome.tabpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import de.wpsmarthome.tabpager.R;
import de.wpsmarthome.tabpager.utils.Context;
import de.wpsmarthome.tabpager.utils.Control;

public class ControlFragment extends SherlockFragment {

	public static final String CONTEXT = "context";
	public static final String CONTROL = "control";

	Context mContext;
	Control mControl;

	public ControlFragment() {
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(CONTEXT)) {
			mContext = (Context)(getArguments().getSerializable(CONTEXT));
			Log.d(getClass().getSimpleName(),mContext.toString());
		}
		if (getArguments().containsKey(CONTROL)){
			mControl = (Control)(getArguments().getSerializable(CONTROL));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_control,
		 container, false);
		if (mContext != null && mControl != null) {
			TextView tw = (TextView) rootView.findViewById(R.id.control);
			tw.setText(mContext + " " + mControl);
		}

		return rootView;
	}


}
