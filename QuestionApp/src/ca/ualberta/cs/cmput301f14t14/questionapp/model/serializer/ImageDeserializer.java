package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;

import android.util.Base64;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ImageDeserializer implements JsonDeserializer<Image>{
	
	@Override
	public Image deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();
		String imageString = jsonObject.get("image").getAsString();
		byte[] b = Base64.decode(imageString, Base64.DEFAULT);
		return new Image(b);
	}

}
