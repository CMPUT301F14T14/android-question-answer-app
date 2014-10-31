package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Answer extends Model {

	private Image mImage;
	private String mBody;
	private List<Comment> commentList;
	private UUID mId;
	private String mAuthor;

	
	public Answer(String body, Image image) {
		mBody = body;
		mImage = image;
		this.setId(new UUID(0, 0));
		setCommentList(new ArrayList<Comment>());
	}
	
	public Image getImage() {
		return mImage;
	}
	
	public String getBody() {
		return mBody;
	}
	
	public String getAuthor() {
		return mAuthor;
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

	public UUID getId() {
		return mId;
	}

	public void setId(UUID mId) {
		this.mId = mId;
	}
	
	public Comment getComment(UUID Cid){
		Iterator<Comment> list = commentList.iterator();
		while(list.hasNext()){
			Comment comment = list.next();
			UUID cid = comment.getmId();
			if(cid.equals(Cid)){
				return comment;
			}
		}
		return null;
	}

}
