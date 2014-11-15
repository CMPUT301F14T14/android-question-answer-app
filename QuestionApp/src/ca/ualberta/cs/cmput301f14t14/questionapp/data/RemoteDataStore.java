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

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.QuestionDeserializer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.serializer.QuestionSerializer;

public class RemoteDataStore implements IDataStore {

	protected static String ES_BASE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t14/";
	private static final String QUESTION_PATH = "question/";
	private static final String TAG = "RemoteDataStore";

	private Gson gson;
	
	public RemoteDataStore() {
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Question.class, new QuestionSerializer());
		gb.registerTypeAdapter(Question.class, new QuestionDeserializer());
		gson = gb.create();
	}

	public void putQuestion(Question question) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(ES_BASE_URL + QUESTION_PATH + question.getId());

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
		HttpGet httpGet = new HttpGet(ES_BASE_URL + QUESTION_PATH + id.toString());

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			@SuppressWarnings("unchecked")
			SearchHit<Question> sr =
				(SearchHit<Question>) parseESResponse(response, new TypeToken<SearchHit<Question>>(){}.getType());
			return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}
	
	public void putAnswer(Answer mAnswer) {
		// TODO Auto-generated method stub
		
	}

	public boolean isComment(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Question> getQuestionList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putQComment(Comment<Question> comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putAComment(Comment<Answer> comment) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	
	private SearchHit<?> parseESResponse(HttpResponse response, Type type) {
		
		try {
			String json = getEntityContent(response);
			
			SearchHit<?> sr = gson.fromJson(json, type);
			return sr;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets content from an HTTP response
	 */
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}
}
