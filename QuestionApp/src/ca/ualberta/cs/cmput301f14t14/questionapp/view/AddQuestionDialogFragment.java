package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
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

public class AddQuestionDialogFragment extends DialogFragment
implements IView{

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final Context context = this.getActivity().getApplicationContext();
		final View text = inflater.inflate(R.layout.addquestiondialogfragmentlayout , null);
		
		builder.setView(text)
			.setPositiveButton(R.string.OK, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							DataManager datamanager = DataManager.getInstance(context);
							EditText title = (EditText) text.findViewById(R.id.add_question_title);
							EditText body = (EditText) text.findViewById(R.id.add_question_body);
							String bd = body.getText().toString();
							String tle = title.getText().toString();
							Question newQues = new Question(tle, bd, datamanager.getUsername(), null);
							datamanager.addQuestion(newQues);
							MainActivity a = (MainActivity) getActivity();
							Question q = datamanager.getQuestion(newQues.getId());
							a.updateList(q);
						}
					}
			).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Do nothing
						}
					}
					);
	return builder.create();				
	}

	
	
}
