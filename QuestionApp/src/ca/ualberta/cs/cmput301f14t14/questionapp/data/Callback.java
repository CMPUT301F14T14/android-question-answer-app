package ca.ualberta.cs.cmput301f14t14.questionapp.data;

/**
 * Interface for DataManager call-backs.
 *
 * run() will be called by the onPostExecute method of AsyncTasks
 * within implementations of DataManager functions.
 *
 * @param <T> Return type of DataManager call.
 */
public interface Callback<T> {
	/**
	 * Run by the onPostExecute method of a Task
	 * @param object
	 */
	public void run(T object);
}
