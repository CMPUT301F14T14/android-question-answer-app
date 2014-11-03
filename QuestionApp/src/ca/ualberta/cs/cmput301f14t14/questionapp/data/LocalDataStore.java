package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class LocalDataStore implements IDataStore {

	private Map<UUID, Question> questions;
	private Map<UUID, Answer> answers;
	private Map<UUID, Comment<Question>> qcomments;
	private Map<UUID, Comment<Answer>> acomments;

	public LocalDataStore() {
		questions = new HashMap<UUID, Question>();
		answers = new HashMap<UUID, Answer>();
		qcomments = new HashMap<UUID, Comment<Question>>();
		acomments = new HashMap<UUID, Comment<Answer>>();
	}

	public List<Question> getQuestionList(){
		return new ArrayList<Question>(questions.values());
	}
	
	public void save(List<Question> questionList){
		// TODO Auto-generated method stub
	}

	public void putQuestion(Question question) {
		questions.put(question.getId(), question);
	}

	public Question getQuestion(UUID id) {
		return questions.get(id);
	}

	public void putAnswer(Answer answer) {
		answers.put(answer.getId(), answer);
	}
	
	public Answer getAnswer(UUID id) {
		return answers.get(id);
	}

	public void putQComment(Comment<Question> comment) {
		qcomments.put(comment.getId(), comment);
	}
	
	public Comment<Question> getQComment(UUID id) {
		return qcomments.get(id);
	}

	public void putAComment(Comment<Answer> comment) {
		acomments.put(comment.getId(), comment);
	}
	
	public Comment<Answer> getAComment(UUID id) {
		return acomments.get(id);
	}

	public void clear() {
		questions.clear();
		answers.clear();
		qcomments.clear();
		acomments.clear();
	}
	
}
