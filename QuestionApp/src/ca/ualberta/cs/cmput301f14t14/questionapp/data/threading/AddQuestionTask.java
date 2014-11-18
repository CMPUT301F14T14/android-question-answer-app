package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.content.Context;

public class AddQuestionTask extends AbstractDataManagerTask<Question, Void, Void>{

	public AddQuestionTask(Context c) {
		super(c);
	}

	@Override
	protected Void doInBackground(Question... qin) {
		Question q = qin[0]; // Ignore other questions inputted
		IDataStore remote = DataManager.getInstance(this.getContext())
			.getRemoteDataStore();
		remote.putQuestion(q);
		remote.save();
		
		return null;
	}

}
