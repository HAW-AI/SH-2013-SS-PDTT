package de.wpsmarthome.tabpager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarDialog extends AlertDialog {
	public interface OnProgressSetListener {
		void onProgressSet(SeekBar view, int progress);
	}
	
	OnProgressSetListener mCallBack;
	SeekBar mSeekBar;
	
	public SeekBarDialog(Context context, final OnProgressSetListener callBack, int titleId, int progress) {
		super(context);
		
		setTitle(titleId);
		
		View view = getLayoutInflater().inflate(R.layout.seek_bar_dialog, null, false);
		setView(view);
		
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
		
		setButton(BUTTON_POSITIVE, context.getText(android.R.string.ok), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				callBack.onProgressSet(seekBar, seekBar.getProgress());
			}
		});
		setButton(BUTTON_NEGATIVE, context.getText(android.R.string.cancel), (OnClickListener) null);
	}


	
}
