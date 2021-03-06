package de.wpsmarthome.tabpager;

import java.util.Locale;

import de.wpsmarthome.control.Messages;
import de.wpsmarthome.control.Objects.Blind;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class BlindControlFragment extends ControlFragment implements ChoiceDialogFragment.OnChoiceListener {

    public static final String BLIND = "blind";
    public static final String BLIND_VALUE = "blind_value";

	private final String simpleClassName = getClass().getSimpleName();
    
	private Blind mBlind;
    private TextView mHeightSummary;
    private int mHeightValue = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mBlind = (Blind) getArguments().getSerializable(BLIND);
        
        if (savedInstanceState != null) {
        	mHeightValue = savedInstanceState.getInt(BLIND_VALUE);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putInt(BLIND_VALUE, mHeightValue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_control_blind,
         container, false);

        final View heightGroup = (View) rootView.findViewById(R.id.blindHeightGroup);
        mHeightSummary = (TextView) rootView.findViewById(R.id.blindHeightSummary);
        mHeightSummary.setText(summaryForHeight(mHeightValue));
        heightGroup.setOnClickListener(new OnClickListener() {
            // must be local or it will only work correctly on the first time
            Drawable selector = new ListView(BlindControlFragment.this.getActivity()).getSelector();
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
		ChoiceDialogFragment f = ChoiceDialogFragment.newInstance(R.string.blindHeightTitle, R.array.blindHeightChoices);
		f.setTargetFragment(this, 0);
		f.show(getFragmentManager(), "blindsHeightChoiceDialogFragment");
	}

	@Override
	public void onChoice(ChoiceDialogFragment dialogFragment, int choice) {
		Log.d(simpleClassName, String.format(Locale.ENGLISH, "onChoice(f, %d)", choice));
		mHeightValue = choice;
		mHeightSummary.setText(summaryForHeight(choice));
		Messages.blindHeightMessage(mBlind, choice).send();
	}

	private CharSequence summaryForHeight(int choice) {
		String[] choices = getResources().getStringArray(R.array.blindHeightChoices);
		return choices[choice];
	}
}
