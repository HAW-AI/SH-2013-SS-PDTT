package de.wpsmarthome.tabpager;

import java.util.Locale;

import de.wpsmarthome.control.Messages;
import de.wpsmarthome.control.Objects.Window;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class WindowControlFragment extends ControlFragment implements ChoiceDialogFragment.OnChoiceListener {

	public static final String WINDOW = "window";
	public static final String WINDOW_STATE = "window_state";
	
    private final String simpleClassName = getClass().getSimpleName();
    
    private static final int sLeaveAjarChoice = 0;
    private static final int sCloseChoice = 1;
    
    private Window mWindow;
    private TextView mStateSummary;
    private int mStateValue = sCloseChoice;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mWindow = (Window) getArguments().getSerializable(WINDOW);
        
        if (savedInstanceState != null) {
        	mStateValue = savedInstanceState.getInt(WINDOW_STATE);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putInt(WINDOW_STATE, mStateValue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_control_window,
         container, false);

        final View stateGroup = (View) rootView.findViewById(R.id.windowStateGroup);
        mStateSummary = (TextView) rootView.findViewById(R.id.windowStateSummary);
        mStateSummary.setText(summaryForState(mStateValue));
        stateGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(WindowControlFragment.this.getActivity()).getSelector();
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.d(simpleClassName, "stateGroup.onClick(v)");
                stateGroup.setBackgroundDrawable(selector);
                showStateDialog();
            }
        });

        return rootView;
    }

	private void showStateDialog() {
		ChoiceDialogFragment f = ChoiceDialogFragment.newInstance(R.string.windowStateTitle, R.array.windowStateChoices);
		f.setTargetFragment(this, 0);
		f.show(getFragmentManager(), "windowStateChoiceDialogFragment");
	}

	@Override
	public void onChoice(ChoiceDialogFragment dialogFragment, int choice) {
		Log.d(simpleClassName, String.format(Locale.ENGLISH, "onChoice(f, %d)", choice));
		mStateValue = choice;
		mStateSummary.setText(summaryForState(choice));
		Messages.windowStateMessage(mWindow, choice == sLeaveAjarChoice).send();
	}

	private CharSequence summaryForState(int choice) {
		String[] choices = getResources().getStringArray(R.array.windowStateChoices);
		return choices[choice];
	}
}
