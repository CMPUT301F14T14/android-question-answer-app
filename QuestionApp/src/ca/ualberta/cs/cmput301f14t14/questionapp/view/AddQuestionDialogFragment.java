package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.io.File;

import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddQuestionDialogFragment extends DialogFragment
implements IView{
	private LayoutInflater inflater;
	private View text;
	private Image img = null;
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final Context context = this.getActivity().getApplicationContext();
		inflater = getActivity().getLayoutInflater();
		text = inflater.inflate(R.layout.addquestiondialogfragmentlayout , null);
		builder.setView(text)
			.setPositiveButton(R.string.OK, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//Create a new question from data given
							DataManager datamanager = DataManager.getInstance(context);
							EditText title = (EditText) text.findViewById(R.id.add_question_title);
							EditText body = (EditText) text.findViewById(R.id.add_question_body);
							String bd = body.getText().toString();
							String tle = title.getText().toString();
							if (bd.isEmpty() || tle.isEmpty()) {
								Toast.makeText(getActivity(), "Questions must have both a valid title and a valid body", Toast.LENGTH_LONG).show();
								return;
							}
							ClientData cd = new ClientData(context);
							Question newQues = new Question(tle, bd, cd.getUsername(), img);
							datamanager.addQuestion(newQues, null);
							MainActivity a = (MainActivity) getActivity();
							//Populate the list with what we have.
							a.updateList(newQues);
						}
					}
			).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Do nothing
						}
					}
					);
	return builder.create();				
	}
	
	public void onResume(){
		super.onResume();
		MainActivity ma = (MainActivity) getActivity();
		img = ma.img;
		
		if(img != null){
			File imgFile = new File(img.getLocalUrl().getPath());
			long len = imgFile.length();
			
			ImageView imgV = (ImageView) text.findViewById(R.id.imageView1);
			//imgV.setImageBitmap(bp);
		}
		}
		

}
