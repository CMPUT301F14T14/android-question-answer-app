package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AbstractEvent;
import android.content.Context;
import android.os.AsyncTask;

/**
 * An AsyncTask that will run the provided callback in its onPostExecute section
 * @param <S> Task parameter type
 * @param <T> Task progress response type
 * @param <V> Task return value type
 */
public abstract class AbstractDataManagerTask<S,T,V> extends AsyncTask<S,T,V> {
	
	protected Context context;
	protected Callback<V> callback = null;
	
	public AbstractDataManagerTask(Context c) {
		this(c, null);
	}

	public AbstractDataManagerTask(Context context, Callback<V> callback) {
		this.context = context;
		this.callback = callback;
	}
	
	protected Context getContext() {
		return context;
	}
	
	public void setCallBack(Callback<V> c) {
		callback = c;
	}

	/**
	 * Runs doInBackground in the current thread.
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
	
	/* Log the pushDelayed event to the bus, and start the uploader service */
	protected void tryPushLater(AbstractEvent e) {
		EventBus.getInstance().addEvent(e);
		DataManager.getInstance(getContext()).startUploaderService();
	}
}
