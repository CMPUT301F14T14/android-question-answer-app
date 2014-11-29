package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCommentDialogFragment extends DialogFragment {

	private View viewComment;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		DataManager datamanager = DataManager.getInstance(getActivity());

		builder.setTitle(R.string.viewing_comments);
		viewComment = inflater
				.inflate(R.layout.viewcommentdialogfragmentlayout,
						(ViewGroup) getView());
		builder.setView(viewComment);

		// Get arguments
		UUID questionId = null, answerId = null, commentId = null;
		String qIdString = getArguments().getString("questionId");
		String aIdString = getArguments().getString("answerId");
		String cIdString = getArguments().getString("commentId");

		if (qIdString != null)
			questionId = UUID
					.fromString(getArguments().getString("questionId"));
		if (aIdString != null)
			answerId = UUID.fromString(getArguments().getString("answerId"));
		if (cIdString != null)
			commentId = UUID.fromString(getArguments().getString("commentId"));

		if (commentId == null || (questionId == null && answerId == null)) {
			Toast.makeText(getActivity(), "Invalid parameters",
					Toast.LENGTH_SHORT).show();
			dismiss();
		}
		if (answerId != null && questionId == null) {
			datamanager.getAnswerComment(commentId, new GetCommentCallback());
		} else {
			datamanager.getQuestionComment(commentId, new GetCommentCallback());
		}

		return builder.create();
	}

	class GetCommentCallback implements
			Callback<Comment<? extends ICommentable>> {

		@Override
		public void run(Comment<? extends ICommentable> comment) {
			TextView username = (TextView) viewComment
					.findViewById(R.id.comment_username);
			username.setText(comment.getAuthor());

			TextView body = (TextView) viewComment
					.findViewById(R.id.comment_body);
			body.setText(comment.getBody());
		}

	}
}
