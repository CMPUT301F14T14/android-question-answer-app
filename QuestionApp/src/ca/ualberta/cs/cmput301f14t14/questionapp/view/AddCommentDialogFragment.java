package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;

public class AddCommentDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View viewComment = inflater.inflate(
				R.layout.addcommentdialogfragmentlayout, (ViewGroup) getView());

		builder.setPositiveButton(R.string.OK,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditText body = (EditText) viewComment
								.findViewById(R.id.add_comment_body);

						((AddCommentDialogCallback) getActivity())
								.addCommentCallback(body.getText().toString());
					}
				})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).setView(viewComment);

		return builder.create();
	}

	public interface AddCommentDialogCallback {
		public void addCommentCallback(String text);
	}
}
