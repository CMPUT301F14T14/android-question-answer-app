package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ImageSerializer implements JsonSerializer<Image>{

	@Override
	public JsonElement serialize(Image im, Type typeOfImage,
		JsonSerializationContext context) {
		final JsonObject encodedImage = new JsonObject();
		encodedImage.addProperty("image", Base64.encodeToString(im.getImageData(), Base64.DEFAULT));
		return encodedImage;
	}

	
	
}
