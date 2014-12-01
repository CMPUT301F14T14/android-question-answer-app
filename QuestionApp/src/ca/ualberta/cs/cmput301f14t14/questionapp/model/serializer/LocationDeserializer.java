package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;

import android.location.Location;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class LocationDeserializer implements JsonDeserializer<Location>{
	
	@Override
	public Location deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();
		String LocationString = jsonObject.get("location").getAsString();
		String[] latLon = LocationString.split(" ");
		double latitude = Double.valueOf(latLon[0]);
		double longitude = Double.valueOf(latLon[1]);
		Location loc = new Location("");
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		
		return loc;
	}

}