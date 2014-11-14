package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Comment<T extends Model> implements Serializable {
	
	private static final long serialVersionUID = 2455600018596168474L;

	private String body;
	private String username;
	private UUID id;
	private UUID parent;
	private Date date;
	
	public Comment(UUID parent, String body, String username) {
		setId(UUID.randomUUID());
		this.body = body;
		this.username = username;
		this.parent = parent;
		setDate(new Date());
	}

	public String getBody() {
		return body;
	}

	public String getUsername() {
		return username;
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
		return c.id.equals(this.id) && c.body.equals(this.body) && c.username.equals(this.username); 
	}
	
	//Return string representation of a comment
	@Override
	public String toString() {
		return String.format("Comment [%s - %s]", body, username);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
