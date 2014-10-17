package ca.ualberta.cs.cmput301f14t14.questionapp.model;


import ca.ualberta.cs.cmput301f14t14.questionapp.view.IView;

public class Question extends Model {
	
	private Integer mId;
	private String mTitle;
	private String mBody;
	private Image mImage;
	
	public Question(String t, String b, Image i) {
		super();
		mTitle = t;
		mBody = b;
		mImage = i;
		mId = generateId();
	}
	
	public void addAnswer(Answer a) {
		
	}
	
	public boolean hasAnswer(Answer a) {
		return false;
	}

	public String getTitle() {
		return mTitle;
	}
	
	public String getBody() {
		return mBody;
	}
	
	public Image getImage() {
		return mImage;
	}
	
	public Integer getId() {
		return mId;
	}
	
	private Integer generateId() {
		//Random r = new Random();
		//return r.nextInt();
		return null;
	}
	@Override
	public void registerView(IView v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterView(IView v) {
		// TODO Auto-generated method stub

	}

}
