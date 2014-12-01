package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.Serializable;

import android.location.Location;

public class LocationHolder implements Serializable {

	/**
	 * Class that holds latitude and longitude and generates a Location when needed. Allows for easy serialization.
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
		if (loc == null){
			return new LocationHolder(0,0);
		}
		return new LocationHolder(loc.getLatitude(), loc.getLongitude());
	}
}
