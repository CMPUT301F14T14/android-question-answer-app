package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetCommentListAnsTask extends AbstractDataManagerTask<Answer, Void, List<Comment<Answer>>> {

	public GetCommentListAnsTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Comment<Answer>> doInBackground(Answer... arg0) {
		Answer a = arg0[0];
		List<Comment<Answer>> comments = new ArrayList<Comment<Answer>>();
		for(UUID c : a.getCommentList()){
			//The null-callback below means that getAnswerComment will be blocking,
			//which is perfectly okay.
			comments.add(DataManager.getInstance(getContext()).getAnswerComment(c,null)); 
		}
		return comments;
	}

	@Override
	protected void onPostExecute(List<Comment<Answer>> lca) {
		if (callback == null){
			return;
		}
		callback.run(lca);
	}
}
