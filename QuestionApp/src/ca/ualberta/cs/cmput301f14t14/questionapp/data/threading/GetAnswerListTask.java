package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetAnswerListTask extends AbstractDataManagerTask<Question, Void, List<Answer>> {

	public GetAnswerListTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Answer> doInBackground(Question... arg0) {
		Question q = arg0[0];
		List<Answer> answers = new ArrayList<Answer>();
		for(UUID a : q.getAnswerList()){
			//Null callback means the line below is blocking. This is fine.
			answers.add(DataManager.getInstance(getContext()).getAnswer(a, null));
		}
		return answers;
	}
	
	@Override
	protected void onPostExecute(List<Answer> la) {
		if (callback == null){
			return;
		}
		callback.run(la);
	}

}
