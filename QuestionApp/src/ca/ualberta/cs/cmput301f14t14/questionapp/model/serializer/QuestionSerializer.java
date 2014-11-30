package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Class used by GSON to serialize Question objects
 */
public class QuestionSerializer implements JsonSerializer<Question> {

	@Override
	public JsonElement serialize(final Question item, final Type type,
			final JsonSerializationContext context) {
		final JsonObject object = new JsonObject();
		object.addProperty("id", item.getId().toString());
		object.addProperty("title", item.getTitle());
		object.addProperty("body", item.getBody());
		// image - don't know what I'm doing with this yet;
		object.addProperty("author", item.getAuthor());
		object.add("date", context.serialize(item.getDate()));
		object.addProperty("upvotes", item.getUpvotes());
		final JsonArray answerList = new JsonArray();
		for (UUID aid: item.getAnswerList()) {
			final JsonPrimitive answerId = new JsonPrimitive(aid.toString());
			answerList.add(answerId);
		}
		object.add("answers", answerList);
		final JsonArray commentList = new JsonArray();
		for (UUID cid: item.getCommentList()) {
			final JsonPrimitive answerId = new JsonPrimitive(cid.toString());
			commentList.add(answerId);
		}
		object.add("comments", commentList);
		
		return object;
	}

}
