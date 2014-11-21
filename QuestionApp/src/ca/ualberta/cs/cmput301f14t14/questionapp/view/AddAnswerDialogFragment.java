package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.QuestionActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.NullCallback;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddAnswerDialogFragment extends DialogFragment implements IView {

	public static final String ARG_QUESTION_ID = "Qid";

	@Override
	public void update() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		final Context context = this.getActivity().getApplicationContext();
		final View text = inflater.inflate(
				R.layout.addanswerdialogfragmentlayout, null);
		
		DialogInterface.OnClickListener confirmListener =
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Creates a new answer with data from dialog fragment
				Answer ans = null;
				Image img = null;
				DataManager datamanager = DataManager.getInstance(context);

				UUID Qid = UUID.fromString(getArguments().getString(ARG_QUESTION_ID));
				EditText body = (EditText) text.findViewById(R.id.add_answer_body);

				ClientData cd = new ClientData(context);

				Question question = datamanager.getQuestion(Qid, new NullCallback<Question>());

				try {
					ans = new Answer(question.getId(), body.getText().toString(), cd.getUsername(), img);
				} catch (IllegalArgumentException e) {
					Toast.makeText(getActivity(),
							R.string.add_answer_err_invalid, Toast.LENGTH_SHORT).show();
					return;
				}
				datamanager.addAnswer(ans);

				((QuestionActivity) getActivity()).updateQuestion(question);
			}
		};
		
		builder.setView(text)
				.setPositiveButton(R.string.OK, confirmListener)
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
