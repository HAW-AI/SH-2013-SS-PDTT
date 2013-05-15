package de.wpsmarthome.tabpager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class LightControlFragment extends ControlFragment {
    
    private final String simpleClassName = getClass().getSimpleName();

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
            }
        });
        
        final View dimmerLabelGroup = (View) rootView.findViewById(R.id.lightDimmerLabelGroup);
        dimmerLabelGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(LightControlFragment.this.getActivity()).getSelector();
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.d(simpleClassName, "dimmerLabelGroup.onClick(v)");
                dimmerLabelGroup.setBackgroundDrawable(selector);
            }
        });
        
        final View colorLabelGroup = (View) rootView.findViewById(R.id.lightColorLabelGroup);
        colorLabelGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(LightControlFragment.this.getActivity()).getSelector();
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.d(simpleClassName, "colorLabelGroup.onClick(v)");
                colorLabelGroup.setBackgroundDrawable(selector);
            }
        });

        return rootView;
    }
    
}
