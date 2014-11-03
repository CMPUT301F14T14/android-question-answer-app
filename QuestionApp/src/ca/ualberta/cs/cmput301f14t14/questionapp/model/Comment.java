package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.UUID;

public class Comment<T extends Model> {
	
	private String body;
	private String username;
	private UUID id;
	private T parent;
	
	public Comment(T parent, String body, String username) {
		setId(UUID.randomUUID());
		this.body = body;
		this.username = username;
		this.parent = parent;
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
	
	public T getParent() {
		return parent;
	}
	
	public void setParent(T parent) {
		this.parent = parent;
	}
}
