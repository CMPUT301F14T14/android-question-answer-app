package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
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
	
	Callback callback = null;
	
	public void setCallBack(Callback c) {
		callback = c;
	}
	/** Runs doInBackground in the current thread.
	 * 
	 * @param param
	 * @return
	 */
	public V blockingRun(S... params){
		return this.doInBackground(params);
	}

	@Override
	protected void onPostExecute(V v) {
		if (callback == null) {
			return;
		}
		callback.run(v);
	}
}
