package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class QuestionListAdapter extends ArrayAdapter<Question> implements IView {

	public QuestionListAdapter(Context context, int resource, List<Question> objects) {
		super(context, resource, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_question, parent, false);
		}
		ClientData cd = new ClientData(getContext());
		Question q = getItem(position);
		if(!cd.getFavoriteQuestions().contains(q.getId())){
			((ImageView)convertView.findViewById(R.id.question_favourite)).setVisibility(View.INVISIBLE);
		}
		else{
			((ImageView)convertView.findViewById(R.id.question_favourite)).setVisibility(View.VISIBLE);
		}
		TextView qTitle = (TextView) convertView.findViewById(R.id.question_title); 
		TextView qText = (TextView) convertView.findViewById(R.id.question_body);
		TextView qAuthor = (TextView) convertView.findViewById(R.id.question_username);
		TextView qDate = (TextView) convertView.findViewById(R.id.question_date);
		TextView qUpVotes = (TextView) convertView.findViewById(R.id.question_upvotes);

		qTitle.setText(q.getTitle());
		qText.setText(q.getBody());
		qAuthor.setText(q.getAuthor());
		qDate.setText(q.getDate().toString());
		qUpVotes.setText(q.getUpvotes().toString());

		
		final ImageButton readLaterbutton = (ImageButton)convertView.findViewById(R.id.list_question_read_later);
		readLaterbutton.setTag(q);
		String readlaterfilename = "readlaterlist";
		ClientData clientData = new ClientData(getContext());
		if(clientData.getItems(readlaterfilename).contains(q.getId())){
			readLaterbutton.setImageResource(R.drawable.ic_read_later_set);
		}
		else{
			readLaterbutton.setImageResource(R.drawable.ic_action_readlater);
		}
		
		readLaterbutton.setFocusable(false);
		readLaterbutton.setOnClickListener(new ImageButton.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Question question = (Question) readLaterbutton.getTag();
				ClientData clientData = new ClientData(getContext());
				String readlaterfilename = "readlaterlist";
				if (clientData.getItems(readlaterfilename).contains(question.getId())) {
					//Question is already flagged read later.
					clientData.unmarkQuestionReadLater(question.getId());
					readLaterbutton.setImageResource(R.drawable.ic_action_readlater);
				} else {
					clientData.markQuestionReadLater(question.getId());
					readLaterbutton.setImageResource(R.drawable.ic_read_later_set);
				}
			}
			
		});
		return convertView;	
	}

	@Override
	public void update() {
		//This is proper MVC
		notifyDataSetChanged();
	}

}
