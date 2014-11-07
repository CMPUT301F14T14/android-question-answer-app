package ca.ualberta.cs.cmput301f14t14.questionapp.view;

public interface IView {
	/**
	 * Called by models to trigger views to update data,
	 * as per Active Model MVC
	 */
	public void update();
}
