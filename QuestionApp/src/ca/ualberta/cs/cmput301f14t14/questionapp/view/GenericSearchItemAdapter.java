package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.GenericSearchItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GenericSearchItemAdapter extends ArrayAdapter<GenericSearchItem> implements IView {

	public GenericSearchItemAdapter(Context context, int resource, List<GenericSearchItem> objects) {
		super(context, resource, objects);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_generic, parent, false);
		}
		GenericSearchItem item = getItem(position);
		ClientData cd = new ClientData(getContext());

		if(!cd.getFavorites().contains(item.getId())){
			((ImageView)convertView.findViewById(R.id.question_favourite)).setVisibility(View.INVISIBLE);
		}
		else{
			((ImageView)convertView.findViewById(R.id.question_favourite)).setVisibility(View.VISIBLE);
		}
		//Fill views with data
		TextView gType = (TextView) convertView.findViewById(R.id.generic_type); 
		TextView gText = (TextView) convertView.findViewById(R.id.generic_body);
		TextView gLocation = (TextView) convertView.findViewById(R.id.generic_location);
		TextView gDate = (TextView) convertView.findViewById(R.id.generic_date);
		gType.setText(item.getTitle() + " <" + item.getType() + ">");
		gText.setText(item.getBody());
		gLocation.setText("near: " + DataManager.getInstance(getContext()).getCityFromLocation(item.getLocation()));
		
		gDate.setText(item.getDate().toString());
		return convertView;
		
	}

	@Override
	public void update() {
		notifyDataSetChanged();
	}

}
