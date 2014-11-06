package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class Question extends Model implements Serializable {

	private static final long serialVersionUID = -8123919371607337418L;

	private UUID id;
	private String title;
	private String body;
	private Image image;
	private String author;
	private List<Answer> answerList; 
	private List<Comment<Question>> commentList;

	public Question() {
		id = new UUID(0L, 0L);
		title = "";
		body = "";
		image = null;
		author = "";
		answerList = new ArrayList<Answer>();
		commentList = new ArrayList<Comment<Question>>();
	}

	public Question(String title, String body, String author, Image image) {
		super();
		setId(UUID.randomUUID());
		setTitle(title);
		setBody(body);
		setAuthor(author);
		setImage(image);
		this.setAnswerList(new ArrayList<Answer>());
		this.setCommentList(new ArrayList<Comment<Question>>());
	}

	public void addAnswer(Answer a) {
		if (!answerList.contains(a)) {
			answerList.add(a);
			a.setParent(this);
		}
	}
	
	public boolean hasAnswer(Answer a) {
		return answerList.contains(a);
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		if (title == null || title.trim().length() == 0)
			throw new IllegalArgumentException("Question title may not be blank.");
		this.title = title.trim();
	}
	
	public String getBody() {
		return body;
	}

	private void setBody(String body) {
		if (body == null || body.trim().length() == 0)
			throw new IllegalArgumentException("Question body may not be blank.");
		this.body = body.trim();
	}
	
	public Image getImage() {
		return image;
	}

	private void setImage(Image image) {
		this.image = image;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public boolean hasComment(Comment<Question> comment) {
		return commentList.contains(comment);
	}

	public void addComment(Comment<Question> comment) {
		if (!commentList.contains(comment)) {
			commentList.add(comment);
			comment.setParent(this);
		}
	}

	public void addUpvote() {
		// TODO Auto-generated method stub
		
	}

	public Integer getUpvotes() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAuthor() {
		return this.author;
	}

	private void setAuthor(String author) {
		this.author = author;
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
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Question)) return false;
		Question q = (Question) o;
		
		return q.id.equals(this.id) && q.title.equals(this.title) && q.body.equals(this.body) &&
				q.author.equals(this.author) && q.answerList.equals(this.answerList) &&
				q.commentList.equals(this.commentList);
	}

	@Override
	public String toString() {
		return String.format("Question [%s: %s - %s]", title, body, author);
	}

}
