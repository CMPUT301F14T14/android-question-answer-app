package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetCommentListQuesTask extends AbstractDataManagerTask<Question, Void, List<Comment<Question>>> {

	public GetCommentListQuesTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Comment<Question>> doInBackground(Question... arg0) {
		Question q = arg0[0];
		List<Comment<Question>> comments = new ArrayList<Comment<Question>>();
		for(UUID c : q.getCommentList()){
			//The getQuestionComment call below is blocking. This is absolutely fine.
			comments.add(DataManager.getInstance(getContext()).getQuestionComment(c, null));
		}
		return comments;
	}
	

}
