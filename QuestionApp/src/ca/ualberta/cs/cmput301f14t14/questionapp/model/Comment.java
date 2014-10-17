package ca.ualberta.cs.cmput301f14t14.questionapp.model;

public class Comment {
	
	private String mBody;
	private String mUsername;
	
	public Comment(String text, String createdBy) {
		mBody = text;
		mUsername = createdBy;
	}

	public String getBody() {
		return mBody;
	}

	public String getUserName() {
		return mUsername;
	}

	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
