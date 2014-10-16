package ca.ualberta.cs.cmput301f14t14.questionapp.model;

public class Comment {
	
	private String body;
	private String userName;
	
	public Comment(String text, String createdBy) {
		body = text;
		createdBy = userName;
	}

	public String getBody() {
		return body;
	}

	public String getUserName() {
		return userName;
	}
	
	
}
