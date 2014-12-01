package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import android.location.Location;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;

public class Comment<T extends ICommentable> implements Serializable {
	
	private static final long serialVersionUID = 2455600018596168474L;

	private String body;
	private String author;
	private UUID id;
	private UUID parent;
	private Date date;
	private LocationHolder location;

	public Comment() {
		setId(new UUID(0L, 0L));
		this.body = "";
		this.author = "";
		this.parent = null;
		setDate(new Date());
	}
	
	public Comment(UUID parent, String body, String username) {
		setId(UUID.randomUUID());
		this.body = body;
		this.author = username;
		this.parent = parent;
		setDate(new Date());
	}

	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String username) {
		this.author = username;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID getParent() {
		return parent;
	}
	
	public void setParent(UUID parent) {
		this.parent = parent;
	}

	//Check the attributes against each other and returns true if . Returns false otherwise or if a 
	//non comment is equated
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Comment<?>)) return false;
		@SuppressWarnings("unchecked")
		Comment<T> c = (Comment<T>) o;
		return c.id.equals(this.id) && c.body.equals(this.body) && c.author.equals(this.author); 
	}
	
	//Return string representation of a comment
	@Override
	public String toString() {
		return String.format("Comment [%s - %s]", body, author);
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
