package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Class to handle searching from ElasticSearch
 */
public class ESSearch {

	protected static String ES_BASE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t14/";

	private Context context;
	private Gson gson;

	public ESSearch(Context context) {
		this.context = context;
		gson = new Gson();
	}

	public List<GenericSearchItem> search(String query) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(ES_BASE_URL + "_search");

		httpPost.setEntity(new StringEntity(
				"{\"query\": {\"filtered\": {\"query\": {\"match\": {\"_all\": \"" + query + "\"}}," +
						"\"filter\": {\"or\": [" +
						"{\"type\": {\"value\": \"question\"}}," +
						"{\"type\": {\"value\": \"answer\"}}" +
						"]}}}}"));
		
		HttpResponse response;

		try {
			response = httpClient.execute(httpPost);
			return getResultList(response, new TypeToken<SearchResponse<GenericSearchItem>>() {}.getType());
		} catch (IOException e) {
			throw new IOException("Search failed.", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<GenericSearchItem> searchNearby(String query, Location location) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(ES_BASE_URL + "_search");

		httpPost.setEntity(new StringEntity(
				"{\"query\":" +
						"{\"filtered\":" +
							"{\"query\":" +
								"{\"match\": {\"_all\": \"" + query + "\"}}," +
							"\"filter\": {" +
								"\"and\": [" +
									"{\"or\": [" +
										"{\"type\": {\"value\": \"question\"}}," +
										"{\"type\": {\"value\": \"answer\"}}" +
									"]}, " +
									"{\"geo_distance\": {" +
										"\"distance\": \"30km\"," +
										"\"location.location\": {" +
											"\"lat\": "+ location.getLatitude() +"," +
											"\"lon\": " + location.getLongitude() + "}" +
										"}" +
									"}" +
								"]" +
							"}" +
						"}" +
					"}"));
		
		HttpResponse response;

		try {
			response = httpClient.execute(httpPost);
			return getResultList(response, new TypeToken<SearchResponse<GenericSearchItem>>() {}.getType());
		} catch (IOException e) {
			throw new IOException("Search failed.", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
	 * Extract a result list from an http response, set type
	 * @param response
	 * @param type Expected type of deserialized http response body
	 * @return
	 */
	private List<GenericSearchItem> getResultList(HttpResponse response, Type type) {
		SearchResponse<GenericSearchItem> sr = parseESResponse(response, type);
		List<SearchHit<GenericSearchItem>> hits = sr.getHits().getHits();
		List<GenericSearchItem> result = new ArrayList<GenericSearchItem>();
		for (SearchHit<GenericSearchItem> hit: hits) {
			GenericSearchItem item = hit.getSource();
			item.setType(hit.get_type());
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
