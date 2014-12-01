package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.AnswerDeserializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.CommentDeserializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.ImageDeserializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.ImageSerializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.QuestionDeserializer;

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
		gb.registerTypeAdapter(Question.class, new QuestionDeserializer(context));
		gb.registerTypeAdapter(Answer.class, new AnswerDeserializer(context));
		gb.registerTypeAdapter(new TypeToken<Comment<Question>>() {}.getType(),
				new CommentDeserializer<Question>());
		gb.registerTypeAdapter(new TypeToken<Comment<Answer>>() {}.getType(),
				new CommentDeserializer<Answer>());
		gb.registerTypeAdapter(Image.class, new ImageSerializer());
		gb.registerTypeAdapter(Image.class, new ImageDeserializer());
		gson = gb.create();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This implementation fetches questions from an ElasticSearch
	 * server using its search interface.
	 */
	@Override
	public List<Question> getQuestionList() throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ES_BASE_URL + QUESTION_PATH + "_search");

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			return getResultList(response, new TypeToken<SearchResponse<Question>>() {}.getType());
		} catch (IOException e) {
			throw new IOException("Error getting question list.", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * This implementation gets all answer children of a question from
	 * an ElasticSearch server.
	 */
	public List<Answer> getAnswerList(Question question) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(ES_BASE_URL + ANSWER_PATH + "_search");
		
		httpPost.setEntity(new StringEntity(
				"{\"query\": {\"has_parent\": {\"type\": \"question\", \"query\": {" +
				"\"match\": {\"id\": \"" + question.getId() + "\"}}}}}"));

		HttpResponse response;

		try {
			response = httpClient.execute(httpPost);
			return getResultList(response, new TypeToken<SearchResponse<Answer>>() {}.getType());
		} catch (IOException e) {
			throw new IOException("Error getting answer list.", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * This implementation gets all comment children of a question from
	 * an ElasticSearch server.
	 */
	public List<Comment<Question>> getCommentList(Question question) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(ES_BASE_URL + QUESTION_COMMENT_PATH + "_search");
		
		httpPost.setEntity(new StringEntity(
				"{\"query\": {\"has_parent\": {\"type\": \"question\", \"query\": {" +
				"\"match\": {\"id\": \"" + question.getId() + "\"}}}}}"));

		HttpResponse response;

		try {
			response = httpClient.execute(httpPost);
			return getResultList(response, new TypeToken<SearchResponse<Comment<Question>>>() {}.getType());
		} catch (IOException e) {
			throw new IOException("Error getting comment list.", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * This implementation gets all comment children of a question from
	 * an ElasticSearch server.
	 */
	public List<Comment<Answer>> getCommentList(Answer answer) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(ES_BASE_URL + ANSWER_COMMENT_PATH + "_search");
		
		httpPost.setEntity(new StringEntity(
				"{\"query\": {\"has_parent\": {\"type\": \"answer\", \"query\": {" +
				"\"match\": {\"id\": \"" + answer.getId() + "\"}}}}}"));

		HttpResponse response;

		try {
			response = httpClient.execute(httpPost);
			return getResultList(response, new TypeToken<SearchResponse<Comment<Answer>>>() {}.getType());
		} catch (IOException e) {
			throw new IOException("Error getting comment list.", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	public void putQuestion(Question question) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(ES_BASE_URL + QUESTION_PATH
					+ question.getId() + "?refresh=true");

			StringEntity stringEntity = new StringEntity(gson.toJson(question));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);
		} catch (IOException e) {
			throw new IOException("Failed to upload question", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Question getQuestion(UUID id) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ES_BASE_URL + QUESTION_PATH
				+ id.toString());

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			SearchHit<Question> sr = parseESResponse(response,
					new TypeToken<SearchHit<Question>>() {}.getType());
			return sr.getSource();

		} catch (Exception e) {
			throw new IOException("Failed to get question", e);
		}
	}

	@Override
	public void putAnswer(Answer answer) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(ES_BASE_URL + ANSWER_PATH
					+ answer.getId() + "?refresh=true&parent=" + answer.getParent());

			StringEntity stringEntity = new StringEntity(gson.toJson(answer));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);
			Log.i(TAG, getEntityContent(response));
		} catch (IOException e) {
			throw new IOException("Failed to upload answer", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Answer getAnswer(UUID id) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ES_BASE_URL + ANSWER_PATH
				+ "_search?q=id:" + id.toString());

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			SearchResponse<Answer> sr = parseESResponse(response,
					new TypeToken<SearchResponse<Answer>>() {}.getType());
			SearchHit<Answer> hit = sr.getHits().getHits().get(0);
			return hit.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void putQComment(Comment<Question> comment) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(ES_BASE_URL
					+ QUESTION_COMMENT_PATH + comment.getId() + "?refresh=true&parent=" + comment.getParent());

			StringEntity stringEntity = new StringEntity(gson.toJson(comment,
					new TypeToken<Comment<Question>>(){}.getType()));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (IOException e) {
			throw new IOException("Failed to save question comment", e);
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
					+ "?refresh=true&parent=" + comment.getParent()
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
	public Comment<Question> getQComment(UUID id) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ES_BASE_URL + QUESTION_COMMENT_PATH
				+ "_search?q=id:" + id.toString());

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			SearchResponse<Comment<Question>> sr = parseESResponse(response,
					new TypeToken<SearchResponse<Comment<Question>>>() {}.getType());
			SearchHit<Comment<Question>> hit = sr.getHits().getHits().get(0);
			return hit.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Comment<Answer> getAComment(UUID id) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ES_BASE_URL + ANSWER_COMMENT_PATH
				+ "_search?q=id:" + id.toString());

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			SearchResponse<Comment<Answer>> sr = parseESResponse(response,
					new TypeToken<SearchResponse<Comment<Answer>>>() {}.getType());
			SearchHit<Comment<Answer>> hit = sr.getHits().getHits().get(0);
			return hit.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		}

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
	 * @param type Type of deserialized object
	 * @return SearchHit object from ElasticSearch
	 */
	private <T> T parseESResponse(HttpResponse response, Type type) {

		try {
			String json = getEntityContent(response);

			T sr = gson.fromJson(json, type);
			return sr;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Extract a result list from an http response
	 * @param response
	 * @param type Expected type of deserialized http response body
	 * @return
	 */
	private <T> List<T> getResultList(HttpResponse response, Type type) {
		SearchResponse<T> sr = parseESResponse(response, type);
		List<SearchHit<T>> hits = sr.getHits().getHits();
		List<T> result = new ArrayList<T>();
		for (SearchHit<T> hit: hits) {
			result.add(hit.getSource());
		}
		return result;
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
}
