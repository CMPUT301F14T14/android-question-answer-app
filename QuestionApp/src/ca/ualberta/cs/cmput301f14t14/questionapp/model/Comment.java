package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.UUID;

public class Comment {
	
	private String mBody;
	private String mUsername;
	private UUID mId;
	
	public Comment(String text, String createdBy) {
		mBody = text;
		mUsername = createdBy;
		this.setmId(new UUID(0, 0));
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

	public UUID getmId() {
		return mId;
	}

	public void setmId(UUID mId) {
		this.mId = mId;
	}
	
	
}
