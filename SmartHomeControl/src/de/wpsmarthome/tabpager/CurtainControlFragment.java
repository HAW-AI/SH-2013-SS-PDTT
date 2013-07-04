package de.wpsmarthome.tabpager;

import java.util.Locale;

import de.wpsmarthome.control.Messages;
import de.wpsmarthome.control.Objects.Curtain;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class CurtainControlFragment extends ControlFragment implements ChoiceDialogFragment.OnChoiceListener {

	public static final String CURTAIN = "curtain";
	public static final String CURTAIN_VALUE = "curtain_value";
	
    private final String simpleClassName = getClass().getSimpleName();
    
    private static final int sOpenChoice = 0;
//    private static final int sCloseChoice = 1;
    
    private Curtain mCurtain;
    private TextView mStateSummary;
    private int mStateValue = 0;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mCurtain = (Curtain) getArguments().getSerializable(CURTAIN);
        
        if (savedInstanceState != null) {
        	mStateValue = savedInstanceState.getInt(CURTAIN_VALUE);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putInt(CURTAIN_VALUE, mStateValue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_control_curtain,
         container, false);

        final View heightGroup = (View) rootView.findViewById(R.id.curtainStateGroup);
        mStateSummary = (TextView) rootView.findViewById(R.id.curtainStateSummary);
        mStateSummary.setText(summaryForHeight(mStateValue));
        heightGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(CurtainControlFragment.this.getActivity()).getSelector();
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.d(simpleClassName, "heightGroup.onClick(v)");
                heightGroup.setBackgroundDrawable(selector);
                showHeightDialog();
            }
        });

        return rootView;
    }

	private void showHeightDialog() {
		ChoiceDialogFragment f = ChoiceDialogFragment.newInstance(R.string.curtainStateTitle, R.array.curtainStateChoices);
		f.setTargetFragment(this, 0);
		f.show(getFragmentManager(), "curtainStateChoiceDialogFragment");
	}

	@Override
	public void onChoice(ChoiceDialogFragment dialogFragment, int choice) {
		Log.d(simpleClassName, String.format(Locale.ENGLISH, "onChoice(f, %d)", choice));
		mStateValue = choice;
		mStateSummary.setText(summaryForHeight(choice));
		Messages.curtainStateMessage(mCurtain, choice == sOpenChoice).send();
	}

	private CharSequence summaryForHeight(int choice) {
		String[] choices = getResources().getStringArray(R.array.curtainStateChoices);
		return choices[choice];
	}
}
