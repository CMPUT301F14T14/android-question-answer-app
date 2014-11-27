package ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.util.Log;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Class used by GSON to deserialize Question objects
 */
public class QuestionDeserializer implements JsonDeserializer<Question> {
	private DataManager dm;

	public QuestionDeserializer(Context context) {
		dm = DataManager.getInstance(context);
	}

	public Question deserialize(final JsonElement json, final Type type,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		try {
			Question question;
			UUID qid = UUID.fromString(jsonObject.get("id").getAsString());
			
			// Get existing object if available
			try {
				question = dm.getLocalDataStore().getQuestion(qid);
			} catch (IOException e) {
				Log.e("QuestionDeserializer", "Failed to get question record");
				question = null;
			}
			if (question == null)
				question = new Question();
			
			// Populate object
			question.setId(qid);
			question.setTitle(jsonObject.get("title").getAsString());
			question.setBody(jsonObject.get("body").getAsString());
			question.setAuthor(jsonObject.get("author").getAsString());
			question.setDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(jsonObject.get("date").getAsString()));
			question.setUpvotes(jsonObject.get("upvotes").getAsInt());
			
			// Populate comment list
			List<UUID> commentList = new ArrayList<UUID>();
			JsonArray commentJsonArray = jsonObject.getAsJsonArray("comments");
			for (JsonElement cIdElement: commentJsonArray) {
				commentList.add(UUID.fromString(cIdElement.getAsString()));
			}
			question.setCommentList(commentList);
			
			// Populate answer list
			List<UUID> answerList = new ArrayList<UUID>();
			JsonArray answerJsonArray = jsonObject.getAsJsonArray("answers");
			for (JsonElement aIdElement: answerJsonArray) {
				answerList.add(UUID.fromString(aIdElement.getAsString()));
			}
			question.setAnswerList(answerList);
			return question;
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
