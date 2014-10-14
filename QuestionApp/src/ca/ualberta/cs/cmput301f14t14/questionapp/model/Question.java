package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import ca.ualberta.cs.cmput301f14t14.questionapp.view.IView;

public class Question extends Model {

	public Question(String title, String body, Image image) {
		super();
	}

	public String getTitle() {
		return null;
	}
	
	public String getBody() {
		return null;
	}
	
	public Image getImage() {
		return null;
	}
	
	@Override
	public void registerView(IView v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterView(IView v) {
		// TODO Auto-generated method stub

	}

}
