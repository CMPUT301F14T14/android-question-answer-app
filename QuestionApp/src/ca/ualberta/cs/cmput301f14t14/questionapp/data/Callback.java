package ca.ualberta.cs.cmput301f14t14.questionapp.data;

public interface Callback {
	public void run(Object o);  //Super dangerous, but the callback when created should know
								//What to expect when casting
}
