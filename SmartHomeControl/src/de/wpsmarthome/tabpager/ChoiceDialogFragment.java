package de.wpsmarthome.tabpager;

import com.actionbarsherlock.app.SherlockDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class ChoiceDialogFragment extends SherlockDialogFragment {
	public interface OnChoiceListener {
		void onChoice(ChoiceDialogFragment dialogFragment, int choice);
	}
	
	OnChoiceListener mCallBack;
	
	public static ChoiceDialogFragment newInstance(int titleId, int choicesArrayId) {
		ChoiceDialogFragment f = new ChoiceDialogFragment();
		
		Bundle args = new Bundle();
        args.putInt("titleId", titleId);
        args.putInt("choicesId", choicesArrayId);

        f.setArguments(args);
		
		return f;
	}
	
	public ChoiceDialogFragment() {}
		
	// from http://stackoverflow.com/questions/13238959/how-to-get-button-clicks-in-host-fragment-from-dialog-fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mCallBack = (OnChoiceListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement ChoiceDialogFragment.OnChoiceListener interface");
        }
    }

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int titleId = getArguments().getInt("titleId");
		int choicesId = getArguments().getInt("choicesId");
				
		return new AlertDialog.Builder(getActivity())
			.setTitle(titleId)
			.setItems(choicesId, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mCallBack.onChoice(ChoiceDialogFragment.this, which);
				}
			})
			.setNegativeButton(android.R.string.cancel, null)
			.create();
	}


	
}
