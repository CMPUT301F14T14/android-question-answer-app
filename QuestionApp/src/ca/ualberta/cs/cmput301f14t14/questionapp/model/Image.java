package ca.ualberta.cs.cmput301f14t14.questionapp.model;

public class Image {

	private String mLocalUrl;
	private String mRemoteUrl;
	
	public Image(String local, String remote) {
		mLocalUrl = local;
		mRemoteUrl = remote;
	}
	
	public String getLocalUrl() {
		return mLocalUrl;
	}

	public String getRemoteUrl() {
		return mRemoteUrl;
	}
}
