package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AnswerListAdapter extends ArrayAdapter<Answer> implements IView {

	public AnswerListAdapter(Context context, int resource, List<Answer> objects) {
		super(context, resource, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_answer, parent, false);
		}
		
		Answer a = getItem(position);
		TextView aText = (TextView) convertView.findViewById(R.id.answer_body);
		TextView aAuthor = (TextView) convertView.findViewById(R.id.answer_username);
		aText.setText(a.getBody());
		aAuthor.setText(a.getAuthor());
		
		return convertView;
	}

	@Override
	public void update() {
		//This is Proper MVC
		notifyDataSetChanged();
	}

}
