package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Answer extends Model implements Serializable {

	private static final long serialVersionUID = -237004584128041997L;

	private UUID id;
	private Image image;
	private String body;
	private List<Comment<Answer>> commentList;
	private String author;
	private Question parent;

	
	public Answer() {
		this.id = null;
		this.body = null;
		this.author = null;
		this.image = null;
		this.parent = null;
		this.commentList = new ArrayList<Comment<Answer>>();
	}

	public Answer(Question parent, String body, String author, Image image) {
		setId(UUID.randomUUID());
		setBody(body);
		setAuthor(author);
		setImage(image);
		setParent(parent);
		setCommentList(new ArrayList<Comment<Answer>>());
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		if (body == null || body.trim().length() == 0)
			throw new IllegalArgumentException("Answer body may not be blank.");
		this.body = body;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean hasComment(Comment<Answer> comment) {
		return commentList.contains(comment);
	}

	public void addComment(Comment<Answer> comment) {
		if (!commentList.contains(comment)) {
			commentList.add(comment);
			comment.setParent(this);
		}
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

	public void setId(UUID id) {
		this.id = id;
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

	public String toString() {
		return String.format("Answer [%s - %s]", body, author);
	}
}
