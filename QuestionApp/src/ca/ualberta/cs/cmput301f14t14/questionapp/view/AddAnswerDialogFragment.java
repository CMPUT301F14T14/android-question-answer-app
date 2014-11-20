package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.QuestionActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
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

public class AddAnswerDialogFragment extends DialogFragment implements IView {

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
		builder.setView(text)
				.setPositiveButton(R.string.OK,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Creates a new answer with data from dialog
								// fragment
								DataManager datamanager = DataManager
										.getInstance(context);

								UUID Qid = UUID.fromString(getArguments()
										.getString("Qid"));
								EditText body = (EditText) text
										.findViewById(R.id.add_answer_body);
								String bd = body.getText().toString();

								ClientData cd = new ClientData(context);
								String username = cd.getUsername();

								Question Ques = datamanager.getQuestion(Qid,
										null);

								Image img = null;

								Answer Ans = new Answer(Ques.getId(), bd,
										username, img);
								datamanager.addAnswer(Ans);

								QuestionActivity a = (QuestionActivity) getActivity();

								a.updateQuestion(Ques);

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
