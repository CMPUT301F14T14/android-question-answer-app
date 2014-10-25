package ca.ualberta.cs.cmput301f14t14.questionapp.view;


import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class AddQuestionDialogFragment extends DialogFragment
implements IView{

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		builder.setView(inflater.inflate(R.layout.addquestiondialogfragmentlayout, null))
			.setPositiveButton(R.string.OK, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Add the question. Callback to MainActivity?
							
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

	
	
}
