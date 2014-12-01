package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommentListAdapter<T extends ICommentable> extends ArrayAdapter<Comment<T>> implements IView {

	public CommentListAdapter(Context context, int resource, List<Comment<T>> objects) {
		super(context, resource, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_comment, parent, false);
		}
		//Update views with data
		Comment<T> c = getItem(position);
		TextView cText = (TextView) convertView.findViewById(R.id.comment_body);
		TextView cAuthor = (TextView) convertView.findViewById(R.id.comment_username);
		TextView cLocation = (TextView) convertView.findViewById(R.id.commentLocationText);
		if(c.getLocation() != null){
			cLocation.setText("near: " + DataManager.getInstance(getContext()).getCityFromLocation(c.getLocation()));
		}
		cText.setText(c.getBody());
		cAuthor.setText(c.getAuthor());
		
		return convertView;
	}
	
	@Override
	public void update() {
		//This is proper MVC
		notifyDataSetChanged();
	}

}
