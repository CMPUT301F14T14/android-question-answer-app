package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Class used by GSON to deserialize Answer objects
 */
public class AnswerDeserializer implements JsonDeserializer<Answer> {

	public Answer deserialize(final JsonElement json, final Type type,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		try {
			final Answer answer = new Answer();
			answer.setId(UUID.fromString(jsonObject.get("id").getAsString()));
			answer.setBody(jsonObject.get("body").getAsString());
			answer.setAuthor(jsonObject.get("author").getAsString());
			answer.setDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(jsonObject.get("date").getAsString()));
			answer.setUpvotes(jsonObject.get("upvotes").getAsInt());
			// TODO: Comments
			return answer;
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
