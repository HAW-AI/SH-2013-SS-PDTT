package de.wpsmarthome.tabpager;

import de.wpsmarthome.control.Messages;
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
                Messages.kitchenLightSwitchMessage(isChecked).send();
            }
        });
        
        final View dimmerGroup = (View) rootView.findViewById(R.id.lightDimmerGroup);
        dimmerGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(LightControlFragment.this.getActivity()).getSelector();
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.d(simpleClassName, "dimmerGroup.onClick(v)");
                dimmerGroup.setBackgroundDrawable(selector);
            }
        });
        
        final View colorGroup = (View) rootView.findViewById(R.id.lightColorGroup);
        colorGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(LightControlFragment.this.getActivity()).getSelector();
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.d(simpleClassName, "colorGroup.onClick(v)");
                colorGroup.setBackgroundDrawable(selector);
            }
        });

        return rootView;
    }
    
}
