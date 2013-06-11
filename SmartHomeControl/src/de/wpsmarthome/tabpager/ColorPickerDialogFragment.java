package de.wpsmarthome.tabpager;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.larswerkman.colorpicker.ColorPicker;
import com.larswerkman.colorpicker.SVBar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;

public class ColorPickerDialogFragment extends SherlockDialogFragment {
	public interface OnColorSetListener {
		void onColorSet(ColorPickerDialogFragment dialogFragment, int color); // color is argb
	}
	
	OnColorSetListener mCallBack;
	
	public static ColorPickerDialogFragment newInstance(int titleId, int color) {
		ColorPickerDialogFragment f = new ColorPickerDialogFragment();
		
		Bundle args = new Bundle();
        args.putInt("titleId", titleId);
        args.putInt("color", color);
        
        f.setArguments(args);
		
		return f;
	}
			
	// from http://stackoverflow.com/questions/13238959/how-to-get-button-clicks-in-host-fragment-from-dialog-fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mCallBack = (OnColorSetListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement SeekBarDialogFragment.OnProgressSetListener interface");
        }
    }

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int titleId = getArguments().getInt("titleId");
		int color = getArguments().getInt("color");
		
		View view = getActivity().getLayoutInflater().inflate(R.layout.color_picker_dialog, null, false);
		
		final ColorPicker picker = (ColorPicker) view.findViewById(R.id.picker);
		
		SVBar svBar = (SVBar) view.findViewById(R.id.svbar);
		picker.addSVBar(svBar);

		picker.setOldCenterColor(color);
		picker.setColor(color);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(titleId)
			.setView(view)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mCallBack.onColorSet(ColorPickerDialogFragment.this, picker.getColor());
				}
			})
			.setNegativeButton(android.R.string.cancel, null)
			.create();
	}

}
