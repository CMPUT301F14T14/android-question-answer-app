package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;

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

		try {
			Answer answer;
			UUID aid = UUID.fromString(jsonObject.get("id").getAsString());

			// Get existing object if available
			answer = dm.getLocalDataStore().getAnswer(aid);
			if (answer == null)
				answer = new Answer();

			// Populate object
			answer.setId(UUID.fromString(jsonObject.get("id").getAsString()));
			answer.setBody(jsonObject.get("body").getAsString());
			answer.setAuthor(jsonObject.get("author").getAsString());
			answer.setDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(jsonObject.get("date").getAsString()));
			answer.setUpvotes(jsonObject.get("upvotes").getAsInt());

			// Populate comment list
			List<UUID> commentList = new ArrayList<UUID>();
			JsonArray commentJsonArray = jsonObject.getAsJsonArray("comments");
			for (JsonElement cIdElement: commentJsonArray) {
				commentList.add(UUID.fromString(cIdElement.getAsString()));
			}
			answer.setCommentList(commentList);

			return answer;
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
