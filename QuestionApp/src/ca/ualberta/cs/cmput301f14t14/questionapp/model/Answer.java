package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import ca.ualberta.cs.cmput301f14t14.questionapp.view.IView;

public class Answer extends Model {

	private Image image;
	
	public Answer(String body, Image image) {
		
	}
	
	@Override
	public void registerView(IView v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterView(IView v) {
		// TODO Auto-generated method stub

	}
	
	public Image getImage() {
		return this.image;
	}

}
