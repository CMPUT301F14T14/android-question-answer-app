package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.UUID;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class AddCommentDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		final UUID questionId = UUID.fromString(savedInstanceState.getString("questionId"));
		final UUID answerId = UUID.fromString(savedInstanceState.getString("answerId"));

		final View viewComment = inflater.inflate(R.layout.addcommentdialogfragmentlayout, null);
		builder.setView(viewComment)
		.setPositiveButton(R.string.OK, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							EditText body = (EditText) viewComment.findViewById(R.id.add_comment_body);
						
							DataManager datamanager = DataManager.getInstance(getActivity());
							Question question = datamanager.getQuestion(questionId);
							Answer answer = datamanager.getAnswer(questionId, answerId);
							String username = datamanager.getUsername();
							
							if(answerId != null){
								@SuppressWarnings({ "rawtypes", "unchecked" })
								Comment<Answer> comment = new Comment(answer, body.toString(), username);
								datamanager.addAnswerComment(questionId, answerId, comment);
							}
							else{
								@SuppressWarnings({ "unchecked", "rawtypes" })
								Comment<Question> comment = new Comment(question,body.toString(), username);
								datamanager.addQuestionComment(questionId, comment);
							}
						}
					}
			).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					}
					);
		
	return builder.create();				
	}
}
