package ca.ualberta.cs.cmput301f14t14.questionapp.model;

public class Image {

	private String localUrl;
	private String remoteUrl;
	
	public Image(String local, String remote) {
		localUrl = local;
		remoteUrl = remote;
	}
	
	public String getLocalUrl() {
		return localUrl;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}
}
