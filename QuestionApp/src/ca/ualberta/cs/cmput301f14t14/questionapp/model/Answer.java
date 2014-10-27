package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.ArrayList;
import java.util.List;

public class Answer extends Model {

	private Image mImage;
	private String mBody;
	private List<Comment> commentList;
	
	public Answer(String body, Image image) {
		mBody = body;
		mImage = image;
		setCommentList(new ArrayList<Comment>());
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

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

}
