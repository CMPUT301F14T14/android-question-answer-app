package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Answer extends Model {

	private UUID id;
	private Image image;
	private String body;
	private List<Comment<Answer>> commentList;
	private String author;
	private Question parent;

	
	public Answer(Question parent, String body, String author, Image image) {
		setId(UUID.randomUUID());
		this.body = body;
		this.author = author;
		this.image = image;
		this.parent = parent;
		setCommentList(new ArrayList<Comment<Answer>>());
	}
	
	public Image getImage() {
		return image;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getAuthor() {
		return author;
	}

	public boolean hasComment(Comment<Answer> mComment) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addComment(Comment<Answer> mComment) {
		// TODO Auto-generated method stub
		
	}

	public int getUpvotes() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void addUpvote() {
		// TODO Auto-generated method stub
		
	}

	public List<Comment<Answer>> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment<Answer>> commentList) {
		this.commentList = commentList;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID mId) {
		this.id = mId;
	}
	
	public Question getParent() {
		return parent;
	}
	
	public void setParent(Question parent) {
		this.parent = parent;
	}
	
	public Comment<Answer> getComment(UUID Cid){
		Iterator<Comment<Answer>> list = commentList.iterator();
		while(list.hasNext()){
			Comment<Answer> comment = list.next();
			UUID cid = comment.getId();
			if(cid.equals(Cid)){
				return comment;
			}
		}
		return null;
	}

}
