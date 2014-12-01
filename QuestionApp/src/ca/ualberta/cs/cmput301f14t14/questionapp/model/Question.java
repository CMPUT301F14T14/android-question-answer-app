package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

import android.location.Location;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;


public class Question extends Model implements Serializable, ICommentable {
	
	private static final long serialVersionUID = -8123919371607337418L;

	private UUID id;
	private String title;
	private String body;
	private Image image;
	private String author;
	@SerializedName("answers") private List<UUID> answerList; 
	@SerializedName("comments") private List<UUID> commentList;
	private Date date;
	private int upvotes;
	private LocationHolder location;

	public Question() {
		id = new UUID(0L, 0L);
		title = "";
		body = "";
		image = null;
		author = "";
		answerList = new ArrayList<UUID>();
		commentList = new ArrayList<UUID>();
		setDate(new Date());
		upvotes = 0;
	}

	public Question(String title, String body, String author, Image image) {
		super();
		setId(UUID.randomUUID());
		setTitle(title);
		setBody(body);
		setAuthor(author);
		setImage(image);
		this.setAnswerList(new ArrayList<UUID>());
		this.setCommentList(new ArrayList<UUID>());
		upvotes = 0;
		setDate(new Date());
	}

	//Add an answer if it already hasn't been
	public void addAnswer(UUID a) {
		if (!answerList.contains(a)) {
			answerList.add(a);
		}
	}
	
	public boolean hasAnswer(UUID a) {
		return answerList.contains(a);
	}

	public String getTitle() {
		return title;
	}

	/**
	 * Set the title if there is one and trim the whitespace
	 * @param title
	 */
	public void setTitle(String title) {
		if (title == null || title.trim().length() == 0)
			throw new IllegalArgumentException("Question title may not be blank.");
		this.title = title.trim();
	}
	
	public String getBody() {
		return body;
	}

	/**
	 * Set the body if there is one and trim the whitespace
	 * @param body
	 */
	public void setBody(String body) {
		if (body == null || body.trim().length() == 0)
			throw new IllegalArgumentException("Question body may not be blank.");
		this.body = body.trim();
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public boolean hasComment(UUID comment) {
		return commentList.contains(comment);
	}

	//Add comment if it already hasn't been added
	public void addComment(UUID comment) {
		if (!commentList.contains(comment)) {
			commentList.add(comment);
		}
	}

	public void addUpvote() {
		upvotes++;
	}

	public Integer getUpvotes() {
		return upvotes;
	}
	
	public void setUpvotes(int val) {
		upvotes = val;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<UUID> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<UUID> commentList) {
		this.commentList = commentList;
	}

	public List<UUID> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<UUID> answerList) {
		this.answerList = answerList;
	}
	
	//Check the Question attributes against each other and return true if they are the same. 
	//Returns false if a non question is put in
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Question)) return false;
		Question q = (Question) o;
		
		return q.id.equals(this.id) && q.title.equals(this.title) && q.body.equals(this.body) &&
				q.author.equals(this.author) && q.answerList.equals(this.answerList) &&
				q.commentList.equals(this.commentList);
	}

	//Return the string representation of a question
	@Override
	public String toString() {
		return String.format("Question [%s: %s - %s]", title, body, author);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Location getLocation() {
		if(location == null){
			return null;
		}
		return location.getLocation();
	}

	public void setLocation(LocationHolder lh) {
		this.location = lh;
	}

	public void setLocation(Location location) {
		this.location = LocationHolder.getLocationHolder(location);
	}

}
