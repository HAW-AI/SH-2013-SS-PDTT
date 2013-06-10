package de.wpsmarthome.tabpager;

import com.actionbarsherlock.app.SherlockDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarDialogFragment extends SherlockDialogFragment {
	public interface OnProgressSetListener {
		void onProgressSet(SeekBar view, int progress);
	}
	
	OnProgressSetListener mCallBack;
	SeekBar mSeekBar;
	
	public static SeekBarDialogFragment newInstance(OnProgressSetListener callBack, int titleId, int progress) {
		SeekBarDialogFragment f = new SeekBarDialogFragment();
		f.setCallBack(callBack);
		
		Bundle args = new Bundle();
        args.putInt("titleId", titleId);
        args.putInt("progress", progress);

        f.setArguments(args);
		
		return f;
	}
	
	public SeekBarDialogFragment() {}
	
	// TODO: pass callback via args-bundle, but how?
	private void setCallBack(OnProgressSetListener callBack) {
		mCallBack = callBack;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int titleId = getArguments().getInt("titleId");
		int progress = getArguments().getInt("progress");
		
		View view = getActivity().getLayoutInflater().inflate(R.layout.seek_bar_dialog, null, false);
		
		final TextView value = (TextView) view.findViewById(R.id.value);
		final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				value.setText(progress + "%");
			}
		});
		
		value.setText(progress + "%");
		seekBar.setProgress(progress);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(titleId)
			.setView(view)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mCallBack.onProgressSet(seekBar, seekBar.getProgress());
				}
			})
			.setNegativeButton(android.R.string.cancel, null)
			.create();
	}


	
}
