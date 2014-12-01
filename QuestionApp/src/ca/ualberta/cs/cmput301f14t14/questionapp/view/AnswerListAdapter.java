package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
		TextView aLocation = (TextView) convertView.findViewById(R.id.answerLocationText);
		aText.setText(a.getBody());
		aAuthor.setText(a.getAuthor());
		if(a.getLocation() != null){
			aLocation.setText(a.getLocation().toString());
		}
		
		final ImageButton readLaterbutton = (ImageButton)convertView.findViewById(R.id.list_answer_read_later);
		readLaterbutton.setTag(a);
		String readlaterfilename = "readlaterlist";
		ClientData clientData = new ClientData(getContext());
		if(clientData.getItems(readlaterfilename).contains(a.getParent())){
			readLaterbutton.setImageResource(R.drawable.ic_read_later_set);
		}
		else{
			readLaterbutton.setImageResource(R.drawable.ic_action_readlater);
		}
		
		readLaterbutton.setFocusable(false);
		readLaterbutton.setOnClickListener(new ImageButton.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Answer answer = (Answer) readLaterbutton.getTag();
				//Question question = (Question) readLaterbutton.getTag();
				ClientData clientData = new ClientData(getContext());
				String readlaterfilename = "readlaterlist";
				if (clientData.getItems(readlaterfilename).contains(answer.getParent())) {
					//Question/Answer is already flagged read later.
					clientData.unmarkQuestionReadLater(answer.getParent());
					readLaterbutton.setImageResource(R.drawable.ic_action_readlater);
				} else {
					clientData.markQuestionReadLater(answer.getParent());
					readLaterbutton.setImageResource(R.drawable.ic_read_later_set);
				}
			}
			
		});
		
		return convertView;
	}

	@Override
	public void update() {
		//This is Proper MVC
		notifyDataSetChanged();
	}

}
