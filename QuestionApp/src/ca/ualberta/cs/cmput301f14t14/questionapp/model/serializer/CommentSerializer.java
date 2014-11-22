package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Class used by GSON to serialize Comment objects
 */
public class CommentSerializer<T extends ICommentable> implements JsonSerializer<Comment<T>> {

	@Override
	public JsonElement serialize(final Comment<T> item, final Type type,
			final JsonSerializationContext context) {
		final JsonObject object = new JsonObject();
		object.addProperty("id", item.getId().toString());
		object.addProperty("body", item.getBody());
		object.addProperty("author", item.getUsername());
		object.addProperty("date", item.getDate().toString());
		
		return object;
	}

}
