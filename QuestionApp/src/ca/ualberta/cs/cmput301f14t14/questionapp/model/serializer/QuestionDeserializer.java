package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Class used by GSON to deserialize Question objects
 */
public class QuestionDeserializer implements JsonDeserializer<Question> {

	public Question deserialize(final JsonElement json, final Type type,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		try {
			final Question question = new Question();
			question.setId(UUID.fromString(jsonObject.get("id").getAsString()));
			question.setTitle(jsonObject.get("title").getAsString());
			question.setBody(jsonObject.get("body").getAsString());
			question.setAuthor(jsonObject.get("author").getAsString());
			question.setDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(jsonObject.get("date").getAsString()));
			question.setUpvotes(jsonObject.get("upvotes").getAsInt());
			// TODO: Answers and comments
			return question;
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
