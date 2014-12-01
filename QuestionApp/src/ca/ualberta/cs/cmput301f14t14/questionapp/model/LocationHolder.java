package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.Serializable;

import android.location.Location;

public class LocationHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9097277563557497450L;

	public double latitude;
	public double longitude;
	public LocationHolder(double lat, double lon){
		latitude = lat;
		longitude = lon;
	}
	
	public Location getLocation(){
		Location loc = new Location("");
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		return loc;
	}
	
	public static LocationHolder getLocationHolder(Location loc){
		return new LocationHolder(loc.getLatitude(), loc.getLongitude());
	}
}
