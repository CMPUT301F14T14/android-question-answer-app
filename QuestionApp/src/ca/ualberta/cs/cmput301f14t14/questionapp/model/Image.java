package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import android.net.Uri;

public class Image {

	private Uri mLocalUrl;
	private Uri mRemoteUrl;
	
	public Image(Uri local, Uri remote) {
		setmLocalUrl(local);
		setmRemoteUrl(remote);
	}
	
	private void setmRemoteUrl(Uri remote) {
		
	}

	private void setmLocalUrl(Uri local) {
		// TODO Auto-generated method stub
		
	}

	public Uri getLocalUrl() {
		return mLocalUrl;
	}

	public Uri getRemoteUrl() {
		return mRemoteUrl;
	}
}
