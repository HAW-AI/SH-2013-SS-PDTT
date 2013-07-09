package de.wpsmarthome.tabpager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockDialogFragment;

import de.wpsmarthome.ubisense.Constants;
import de.wpsmarthome.ubisense.xmpp.XmppConstants;

public class SettingsDialogFragment extends SherlockDialogFragment {
	OnIpChangedListener mCallback;
	private EditText ipAddressText;
	private SharedPreferences mPrefs;

	public interface OnIpChangedListener {
		public void storeNewIPAddress(String ipAddress);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnIpChangedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	public SettingsDialogFragment() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.fragment_settings_dialog, null);
		builder.setView(view);

		ipAddressText = (EditText) view.findViewById(R.id.ipAddressText);

		mPrefs = getActivity().getSharedPreferences(
				XmppConstants.SHARED_PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		String xmppIpAddressFromSettingsDialog = mPrefs.getString(Constants.XMPP_IP_FROM_SETTINGS_DIALOG, Constants.XMPP_IP_NOT_PRESENT);
		String xmppIpAddressFromProperties = mPrefs.getString(XmppConstants.XMPP_HOST, "localhost");
		if (xmppIpAddressFromSettingsDialog.equals(Constants.XMPP_IP_NOT_PRESENT)) {
			ipAddressText.setText(xmppIpAddressFromProperties);
		} else {
			ipAddressText.setText(xmppIpAddressFromSettingsDialog);
		}
		ipAddressText.setSelection(ipAddressText.length());

		builder.setTitle(R.string.settingsDialogTitle)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								mCallback.storeNewIPAddress(ipAddressText
										.getText().toString());
							}
						})
				.setNegativeButton(android.R.string.cancel, null);
		
		return builder.create();
	}
}
