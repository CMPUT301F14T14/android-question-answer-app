package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.GenericSearchItem;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.ESSearchTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.GenericSearchItemAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SearchActivity extends Activity {

	public static final String ARG_QUERY_STRING = "QUERY_STRING";

	private GenericSearchItemAdapter listAdapter = null;
	private List<GenericSearchItem> searchResult = null;
	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Intent intent = getIntent();
		query = intent.getStringExtra(ARG_QUERY_STRING);

		searchResult = new ArrayList<GenericSearchItem>();
		listAdapter = new GenericSearchItemAdapter(
				this, R.layout.list_generic, searchResult);
		ListView questionView = (ListView) findViewById(R.id.search_list);
		questionView.setAdapter(listAdapter);

		questionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// run off to the correct view if you tap an item
						final GenericSearchItem gItem = listAdapter.getItem(position);
						
						if (gItem.getType().toLowerCase(Locale.US).trim().equals("question")) {
							UUID qId = gItem.getId();
							Intent intent = new Intent(getApplicationContext(),
									QuestionActivity.class);
							intent.putExtra(QuestionActivity.ARG_QUESTION_ID, qId.toString());
							startActivity(intent);
						} else if (gItem.getType().toLowerCase(Locale.US).trim()
								.equals("answer")) {
							UUID aId = gItem.getId();
							Intent intent = new Intent(getApplicationContext(),
									AnswerViewActivity.class);
							intent.putExtra("ANSWER_UUID", aId.toString());
							startActivity(intent);
						}
					}
				});

	}

	@Override
	protected void onResume() {
		super.onResume();
		ESSearchTask esTask = new ESSearchTask(this);
		esTask.setCallBack(new SearchResultCallback());
		esTask.execute(query);
	}
	
	private class SearchResultCallback implements Callback<List<GenericSearchItem>> {

		@Override
		public void run(List<GenericSearchItem> list) {
			searchResult.clear();
			searchResult.addAll(list);
			listAdapter.update();
		}

	};
}
