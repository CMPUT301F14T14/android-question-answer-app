package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.AnswerDeserializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.AnswerSerializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.CommentDeserializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.CommentSerializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.QuestionDeserializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.QuestionSerializer;

/**
 * A data store that uses an ElasticSearch server as a data storage mechanism.
 */
public class RemoteDataStore implements IDataStore {

	protected static String ES_BASE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t14/";
	private static final String QUESTION_PATH = "question/";
	private static final String ANSWER_PATH = "answer/";
	private static final String QUESTION_COMMENT_PATH = "qcomment/";
	private static final String ANSWER_COMMENT_PATH = "acomment/";
	private static final String TAG = "RemoteDataStore";

	private Context context;
	private Gson gson;

	public RemoteDataStore(Context context) {
		this.context = context;
		GsonBuilder gb = new GsonBuilder();
		// Register serializers and deserializers
		// Note: The comment stuff is ugly, but it should work
		gb.registerTypeAdapter(Question.class, new QuestionSerializer());
		gb.registerTypeAdapter(Question.class, new QuestionDeserializer());
		gb.registerTypeAdapter(Answer.class, new AnswerSerializer());
		gb.registerTypeAdapter(Answer.class, new AnswerDeserializer());
		gb.registerTypeAdapter(new TypeToken<Comment<Question>>() {}.getType(),
				new CommentSerializer<Question>());
		gb.registerTypeAdapter(new TypeToken<Comment<Question>>() {}.getType(),
				new CommentDeserializer<Question>());
		gb.registerTypeAdapter(new TypeToken<Comment<Answer>>() {}.getType(),
				new CommentSerializer<Answer>());
		gb.registerTypeAdapter(new TypeToken<Comment<Answer>>() {}.getType(),
				new CommentDeserializer<Answer>());
		gson = gb.create();
	}

	@Override
	public void putQuestion(Question question) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(ES_BASE_URL + QUESTION_PATH
					+ question.getId());

			StringEntity stringEntity = new StringEntity(gson.toJson(question));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Question getQuestion(UUID id) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ES_BASE_URL + QUESTION_PATH
				+ id.toString());

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			@SuppressWarnings("unchecked")
			SearchHit<Question> sr = (SearchHit<Question>) parseESResponse(
					response, new TypeToken<SearchHit<Question>>() {
					}.getType());
			return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void putAnswer(Answer answer) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(ES_BASE_URL + ANSWER_PATH
					+ answer.getId() + "?parent=" + answer.getParent());

			StringEntity stringEntity = new StringEntity(gson.toJson(answer));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Question> getQuestionList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putQComment(Comment<Question> comment) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(ES_BASE_URL
					+ QUESTION_COMMENT_PATH + comment.getId() + "?parent=" + comment.getParent());

			StringEntity stringEntity = new StringEntity(gson.toJson(comment,
					new TypeToken<Comment<Question>>(){}.getType()));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void putAComment(Comment<Answer> comment) {
		DataManager dm = DataManager.getInstance(context);
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(ES_BASE_URL
					+ ANSWER_COMMENT_PATH + comment.getId()
					+ "?parent=" + comment.getParent()
					+ "&routing=" + dm.getAnswer(comment.getParent(), null).getParent());

			StringEntity stringEntity = new StringEntity(gson.toJson(comment,
					new TypeToken<Comment<Answer>>(){}.getType()));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Answer getAnswer(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment<Question> getQComment(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment<Answer> getAComment(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		// Do nothing. Always saved.
	}

	/**
	 * Parse response from ElasticSearch, which is contained in a SearchHit
	 * object
	 * 
	 * @param response
	 * @param type
	 * @return SearchHit object from ElasticSearch
	 */
	private SearchHit<?> parseESResponse(HttpResponse response, Type type) {

		try {
			String json = getEntityContent(response);

			SearchHit<?> sr = gson.fromJson(json, type);
			return sr;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets content from an HTTP response
	 */
	private String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}

	@Override
	public boolean hasAccess() {
		// TODO Auto-generated method stub
		return false;
	}
}
