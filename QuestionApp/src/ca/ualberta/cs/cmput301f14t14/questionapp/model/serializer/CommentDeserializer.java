package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.LocationHolder;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Class used by GSON to deserialize Answer objects
 */
public class CommentDeserializer<T extends ICommentable> implements JsonDeserializer<Comment<T>> {

	public Comment<T> deserialize(final JsonElement json, final Type type,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final Comment<T> comment = new Comment<T>();
		comment.setId(UUID.fromString(jsonObject.get("id").getAsString()));
		comment.setParent(UUID.fromString(jsonObject.get("parent").getAsString()));
		comment.setBody(jsonObject.get("body").getAsString());
		comment.setAuthor(jsonObject.get("author").getAsString());
		comment.setDate((Date) context.deserialize(jsonObject.get("date"), Date.class));
		comment.setLocation((LocationHolder) context.deserialize(jsonObject.get("location"), LocationHolder.class));
		return comment;
	}

}
