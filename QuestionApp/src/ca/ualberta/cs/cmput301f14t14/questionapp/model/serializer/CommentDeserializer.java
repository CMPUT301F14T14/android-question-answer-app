package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Class used by GSON to deserialize Answer objects
 */
public class CommentDeserializer<T extends Model> implements JsonDeserializer<Comment<T>> {

	public Comment<T> deserialize(final JsonElement json, final Type type,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		try {
			final Comment<T> comment = new Comment<T>();
			comment.setId(UUID.fromString(jsonObject.get("id").getAsString()));
			comment.setBody(jsonObject.get("body").getAsString());
			comment.setUsername(jsonObject.get("author").getAsString());
			comment.setDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(jsonObject.get("date").getAsString()));
			return comment;
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
