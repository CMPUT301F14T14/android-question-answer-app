package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.util.Log;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.LocationHolder;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Class used by GSON to deserialize Answer objects
 */
public class AnswerDeserializer implements JsonDeserializer<Answer> {
	private DataManager dm;

	public AnswerDeserializer(Context context) {
		dm = DataManager.getInstance(context);
	}

	public Answer deserialize(final JsonElement json, final Type type,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		Answer answer;
		UUID aid = UUID.fromString(jsonObject.get("id").getAsString());

		// Get existing object if available
		try {
			answer = dm.getLocalDataStore().getAnswer(aid);
		} catch (IOException e) {
			Log.e("AnswerDeserializer", "Failed to get answer record");
			answer = null;
		}
		if (answer == null)
			answer = new Answer();

		// Populate object
		answer.setId(UUID.fromString(jsonObject.get("id").getAsString()));
		answer.setParent(UUID.fromString(jsonObject.get("parent").getAsString()));
		answer.setBody(jsonObject.get("body").getAsString());
		answer.setAuthor(jsonObject.get("author").getAsString());
		answer.setDate((Date) context.deserialize(jsonObject.get("date"), Date.class));
		answer.setUpvotes(jsonObject.get("upvotes").getAsInt());
		answer.setImage((Image) context.deserialize(jsonObject.get("image"), Image.class));
		answer.setLocation((LocationHolder) context.deserialize(jsonObject.get("location"), LocationHolder.class));

		// Populate comment list
		List<UUID> commentList = new ArrayList<UUID>();
		JsonArray commentJsonArray = jsonObject.getAsJsonArray("comments");
		for (JsonElement cIdElement: commentJsonArray) {
			commentList.add(UUID.fromString(cIdElement.getAsString()));
		}
		answer.setCommentList(commentList);

		return answer;
	}

}
