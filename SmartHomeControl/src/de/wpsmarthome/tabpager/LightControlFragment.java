package de.wpsmarthome.tabpager;

import java.util.Locale;

import de.wpsmarthome.control.Messages;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class LightControlFragment extends ControlFragment
	implements SeekBarDialogFragment.OnProgressSetListener, ColorPickerDialogFragment.OnColorSetListener {
    
    private final String simpleClassName = getClass().getSimpleName();
    private int mDimmerValue = 100;
    private TextView mDimmerSummary;
    private int mColorValue = -1442840577; // white
    private TextView mColorSummary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_control_light,
         container, false);
        
        CompoundButton toggleButton = (CompoundButton) rootView.findViewById(R.id.lightToggleButton);
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(simpleClassName, String.format("toggleButton.onCheckedChanged(this, %b)", isChecked));
                Messages.kitchenLightSwitchMessage(isChecked).send();
            }
        });
        
        final View dimmerGroup = (View) rootView.findViewById(R.id.lightDimmerGroup);
        mDimmerSummary = (TextView) rootView.findViewById(R.id.lightDimmerSummary);
        dimmerGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(LightControlFragment.this.getActivity()).getSelector();
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.d(simpleClassName, "dimmerGroup.onClick(v)");
                dimmerGroup.setBackgroundDrawable(selector);
                showDimmerDialog();
            }
        });
        
        final View colorGroup = (View) rootView.findViewById(R.id.lightColorGroup);
        mColorSummary = (TextView) rootView.findViewById(R.id.lightColorSummary);
        mColorSummary.setText(colorSummary(mColorValue)); // correct formatting
        colorGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(LightControlFragment.this.getActivity()).getSelector();
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.d(simpleClassName, "colorGroup.onClick(v)");
                colorGroup.setBackgroundDrawable(selector);
                showColorDialog();
            }
        });
        
        

        return rootView;
    }

	private void showDimmerDialog() {
		SeekBarDialogFragment f = SeekBarDialogFragment.newInstance(R.string.lightDimmerTitle, mDimmerValue);
		f.setTargetFragment(this, 0);
		f.show(getFragmentManager(), "dimmerSeekBarDialogFragment");
	}

	private void showColorDialog() {
		ColorPickerDialogFragment f = ColorPickerDialogFragment.newInstance(R.string.lightColorTitle, mColorValue);
		f.setTargetFragment(this, 0);
		f.show(getFragmentManager(), "colorSeekBarDialogFragment");
	}

	@Override
	public void onProgressSet(SeekBarDialogFragment dialogFragment, int progress) {
		Log.d(simpleClassName, String.format("onProgressSet(v, %s)", progress));
		mDimmerValue = progress;
		mDimmerSummary.setText(progress + "%");
	}

	@Override
	public void onColorSet(ColorPickerDialogFragment dialogFragment, int color) {
		Log.d(simpleClassName, String.format("onColorSet(f, %s)", color));
		mColorValue = color;
		mColorSummary.setText(colorSummary(color));
	}
	
	private CharSequence colorSummary(int color) {
		// "\u2588" is FULL BLOCK http://www.fileformat.info/info/unicode/char/2588/index.htm
		String summary = String.format(Locale.ENGLISH, "\u2588 (%d, %d, %d)",
				Color.red(color), Color.green(color), Color.blue(color));
		SpannableString spannableSummary = new SpannableString(summary);
		spannableSummary.setSpan(new ForegroundColorSpan(color), 0, 1, 0); // colorize first char
		return spannableSummary;
	}

}
