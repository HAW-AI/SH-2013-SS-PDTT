package de.wpsmarthome.tabpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

public class LightControlFragment extends ControlFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_control_light,
         container, false);
        if (mContext != null && mControl != null) {
            TextView tw = (TextView) rootView.findViewById(R.id.title);
            tw.setText(mContext + " " + mControl);
        }
        
        CompoundButton toggleButton = (CompoundButton) rootView.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onclick");
            }
        });

        return rootView;
    }
    
}
