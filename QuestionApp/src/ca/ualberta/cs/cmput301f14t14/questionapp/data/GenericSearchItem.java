package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.Date;
import java.util.UUID;

import android.location.Location;

/**
 * Value object to keep track of a generic search result
 */
public class GenericSearchItem {
	private UUID id;
	private String title;
	private String body;
	private Date date;
	private Location location;
	private String type;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
