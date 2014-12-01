package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;

import android.location.Location;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocationSerializer implements JsonSerializer<Location> {

	@Override
	public JsonElement serialize(Location loc, Type arg1,
			JsonSerializationContext context) {
			final JsonObject encodedLocation = new JsonObject();
			String encodedLocationString = String.valueOf(loc.getLatitude()) + " " + String.valueOf(loc.getLongitude());
			encodedLocation.addProperty("location", encodedLocationString);
			return encodedLocation;
		}

}


