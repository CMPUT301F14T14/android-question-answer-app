package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import ca.ualberta.cs.cmput301f14t14.questionapp.QuestionActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Fragment that allows inputting data to create an answer.
 */
public class AddAnswerDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View text = inflater.inflate(
				R.layout.addanswerdialogfragmentlayout, (ViewGroup) getView());
		
		builder.setView(text)
				.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Image img = null;
						EditText body = (EditText) text.findViewById(R.id.add_answer_body);
						CheckBox answerLocation = (CheckBox) text.findViewById(R.id.answerLocationBox);
						Location loc = null;
						if(answerLocation.isChecked()){
							LocationManager lm = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
							if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
								loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							}
							else{
								Toast.makeText(getActivity().getApplicationContext(), "Please enable GPS to use Location Service", Toast.LENGTH_SHORT).show();
							
							}
						}
						((QuestionActivity) getActivity()).addAnswerCallback(body.getText().toString(), img, loc);
					}
				})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Do nothing
							}
						});
		return builder.create();
	}

}
