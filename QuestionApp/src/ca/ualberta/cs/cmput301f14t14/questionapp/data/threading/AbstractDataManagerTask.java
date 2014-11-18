package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import android.content.Context;
import android.os.AsyncTask;

public abstract class AbstractDataManagerTask<S,T,V> extends AsyncTask<S,T,V> {
	
	protected Context context;
	
	public AbstractDataManagerTask(Context c) {
		context = c;
	}
	
	protected Context getContext() {
		return context;
	}

}
