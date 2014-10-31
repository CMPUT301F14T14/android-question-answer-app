package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class Question extends Model {
	
	private String mTitle;
	private String mBody;
	private Image mImage;
	private UUID mId;
	private String author;
	private List<Answer> answerList; 
	private List<Comment> commentList;

	public Question(String title, String body, Image image) {
		super();
		this.mTitle = title;
		this.mBody = body;
		this.mImage = image;
		this.mId = new UUID(0, 0);
		this.setAnswerList(new ArrayList<Answer>());
		this.setCommentList(new ArrayList<Comment>());
	}
	
	public void addAnswer(Answer a) {
		
	}
	
	public boolean hasAnswer(Answer a) {
		return false;
	}

	public String getTitle() {
		return mTitle;
	}
	
	public String getBody() {
		return mBody;
	}
	
	public Image getImage() {
		return mImage;
	}
	
	public UUID getId() {
		return mId;
	}
	
	public boolean hasComment(Comment mComment) {
		return false;
		// TODO Auto-generated method stub
		
	}

	public void addComment(Comment mComment) {
		// TODO Auto-generated method stub
		
	}

	public void addUpvote() {
		// TODO Auto-generated method stub
		
	}

	public Integer getUpvotes() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAuthor() {
		// TODO Auto-generated method stub
		return this.author;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}
	
	public Answer getAnswer(UUID Aid){
		Iterator<Answer> list = answerList.iterator();
		while(list.hasNext()){
			Answer answer = list.next();
			UUID aid = answer.getId();
			if(aid.equals(Aid)){
				return answer;
			}
		}
		return null;
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
	
	public void setAnswer(UUID Aid, Answer answer){
		Answer ans = getAnswer(Aid);
		Integer position = answerList.indexOf(ans);
		answerList.set(position, answer);
	}
	


}
