package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class Question extends Model {

	private UUID id;
	private String title;
	private String body;
	private Image image;
	private String author;
	private List<Answer> answerList; 
	private List<Comment<Question>> commentList;

	public Question(String title, String body, String author, Image image) {
		super();
		setId(UUID.randomUUID());
		this.title = title;
		this.body = body;
		this.author = author;
		this.image = image;
		this.setAnswerList(new ArrayList<Answer>());
		this.setCommentList(new ArrayList<Comment<Question>>());
	}

	public void addAnswer(Answer a) {
		
	}
	
	public boolean hasAnswer(Answer a) {
		return false;
	}

	public String getTitle() {
		return title;
	}
	
	public String getBody() {
		return body;
	}
	
	public Image getImage() {
		return image;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public boolean hasComment(Comment<Question> mComment) {
		return false;
		// TODO Auto-generated method stub
		
	}

	public void addComment(Comment<Question> mComment) {
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

	public List<Comment<Question>> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment<Question>> commentList) {
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
	
	public Comment<Question> getComment(UUID Cid){
		Iterator<Comment<Question>> list = commentList.iterator();
		while(list.hasNext()){
			Comment<Question> comment = list.next();
			UUID cid = comment.getId();
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
