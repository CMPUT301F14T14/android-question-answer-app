package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;

public class GetAnswerTask extends AbstractDataManagerTask<UUID, Void, Answer> {

	public GetAnswerTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Answer doInBackground(UUID... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void onPostExecute(Answer a) {
		callback.run(a);
	}

}
