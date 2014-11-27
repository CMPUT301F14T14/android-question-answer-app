package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import ca.ualberta.cs.cmput301f14t14.questionapp.QuestionActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
						((QuestionActivity) getActivity()).addAnswerCallback(body.getText().toString(), img);
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
