package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.AnswerViewActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.QuestionActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.WelcomeScreenActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SearchQueryDialogFragment extends DialogFragment{

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View queryText = inflater.inflate(R.layout.search_fragment, null);
		builder.setTitle(R.string.enter_search_query);
		builder.setView(queryText)
		.setPositiveButton(R.string.enter, 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Fire off to SearchActivity with given phrase
						EditText query = (EditText) queryText.findViewById(R.id.queryText);
						String q = query.getText().toString();
						MainActivity ac = (MainActivity) getActivity();
						ac.searchQuestions(q);
						
					}
						
						
		}
		
		
		).setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Do nothing
					}
		
			
		});
		
		return builder.create();
	}
}
