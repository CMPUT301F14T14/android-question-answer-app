package ca.ualberta.cs.cmput301f14t14.questionapp.model;


public class Question extends Model {
	
	private String mTitle;
	private String mBody;
	private Image mImage;
	private Integer mId;

	public Question(String title, String body, Image image) {
		super();
		this.mTitle = title;
		this.mBody = body;
		this.mImage = image;
		this.mId = generateId();
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

	public boolean hasComment(Comment mComment) {
		return false;
		// TODO Auto-generated method stub
		
	}

	public void addComment(Comment mComment) {
		// TODO Auto-generated method stub
		
	}

	public void addUpvote() {
		// TODO Auto-generated method stub
		
	}

	public Integer getUpvotes() {
		// TODO Auto-generated method stub
		return null;
	}

}
