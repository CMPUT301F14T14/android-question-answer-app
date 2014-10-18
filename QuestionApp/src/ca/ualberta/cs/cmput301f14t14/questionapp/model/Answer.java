package ca.ualberta.cs.cmput301f14t14.questionapp.model;

public class Answer extends Model {

	private Image mImage;
	private String mBody;
	
	public Answer(String body, Image image) {
		mBody = body;
		mImage = image;
	}
	
	public Image getImage() {
		return mImage;
	}

	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getBody() {
		return mBody;
	}

	public boolean hasComment(Comment mComment) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addComment(Comment mComment) {
		// TODO Auto-generated method stub
		
	}

	public int getUpvotes() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void addUpvote() {
		// TODO Auto-generated method stub
		
	}

}
