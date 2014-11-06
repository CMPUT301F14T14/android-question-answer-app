package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class QuestionListAdapter extends ArrayAdapter<Question> implements IView {

	public QuestionListAdapter(Context context, int resource, List<Question> objects) {
		super(context, resource, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_question, parent, false);
		}
		
		Question q = getItem(position); //Emotional Comment
		TextView qTitle = (TextView) convertView.findViewById(R.id.question_title); 
		TextView qText = (TextView) convertView.findViewById(R.id.question_body);
		TextView qAuthor = (TextView) convertView.findViewById(R.id.question_username);
		qTitle.setText(q.getTitle());
		qText.setText(q.getBody());
		qAuthor.setText(q.getAuthor());
		
		return convertView;
	}

	@Override
	public void update() {
		notifyDataSetChanged();
	}

}
