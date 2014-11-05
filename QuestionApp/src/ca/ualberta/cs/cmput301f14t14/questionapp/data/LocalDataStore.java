package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class LocalDataStore implements IDataStore {

	private static final String SAVE_FILE = "local_data.sav";

	private Context context;
	private Map<UUID, Question> questions;
	private Map<UUID, Answer> answers;
	private Map<UUID, Comment<Question>> qcomments;
	private Map<UUID, Comment<Answer>> acomments;

	public LocalDataStore(Context context) {
		this.context = context;
		load();
		buildMaps();
	}

	public List<Question> getQuestionList(){
		return new ArrayList<Question>(questions.values());
	}
	
	public void save() {
		OutputStream os;
		ObjectOutput oo;
		try {
			os = new BufferedOutputStream(context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE));
			oo = new ObjectOutputStream(os);
			try {
				oo.writeObject(questions);
				Log.d("save", "Saved data to file " + SAVE_FILE);
			} finally {
				oo.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void load() {
		InputStream is;
		ObjectInput oi;
		try {
			try {
				is = new BufferedInputStream(context.openFileInput(SAVE_FILE));
			} catch (FileNotFoundException e) {
				Log.d("load", "Serialized file not found.");
				questions = new HashMap<UUID, Question>();
				return;
			}
			oi = new ObjectInputStream(is);
			try {
				questions = (HashMap<UUID, Question>) oi.readObject();
				if (questions == null) {
					Log.e("load", "Loaded data was null. Returning new object.");
					questions = new HashMap<UUID, Question>();
				} else {
					Log.d("load", "Successfully loaded data from file with " + questions.size() + " entries.");
				}
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				oi.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.e("load", "Failed to load TodoList");
		questions = new HashMap<UUID, Question>();
		return;
	}
	
	public void buildMaps() {
		answers = new HashMap<UUID, Answer>();
		qcomments = new HashMap<UUID, Comment<Question>>();
		acomments = new HashMap<UUID, Comment<Answer>>();
		// Build answer map
		for (Question q: questions.values()) {
			for (Answer a: q.getAnswerList()) {
				answers.put(a.getId(), a);
				for (Comment<Answer> c: a.getCommentList()) {
					acomments.put(c.getId(), c);
				}
			}
			for (Comment<Question> c: q.getCommentList()) {
				qcomments.put(c.getId(), c);
			}
		}
	}

	public void putQuestion(Question question) {
		questions.put(question.getId(), question);
		for (Answer a: question.getAnswerList()) {
			if (answers.get(a.getId()) == null) {
				answers.put(a.getId(), a);
			}
		}
		for (Comment<Question> c: question.getCommentList()) {
			if (qcomments.get(c.getId()) == null) {
				qcomments.put(c.getId(), c);
			}
		}
	}

	public Question getQuestion(UUID id) {
		return questions.get(id);
	}

	public void putAnswer(Answer answer) {
		answers.put(answer.getId(), answer);
		if (questions.get(answer.getParent().getId()) == null) {
			putQuestion(answer.getParent());
		}
		for (Comment<Answer> c: answer.getCommentList()) {
			if (acomments.get(c.getId()) == null) {
				acomments.put(c.getId(), c);
			}
		}
	}
	
	public Answer getAnswer(UUID id) {
		return answers.get(id);
	}

	public void putQComment(Comment<Question> comment) {
		qcomments.put(comment.getId(), comment);
		if (questions.get(comment.getParent().getId()) == null) {
			putQuestion(comment.getParent());
		}
	}
	
	public Comment<Question> getQComment(UUID id) {
		return qcomments.get(id);
	}

	public void putAComment(Comment<Answer> comment) {
		acomments.put(comment.getId(), comment);
		if (answers.get(comment.getParent().getId()) == null) {
			putAnswer(comment.getParent());
		}
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
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LocalDataStore)) {
			Log.e("equals", "Object is of incorrect type.");
			return false;
		}
		LocalDataStore lds = (LocalDataStore) o;
		return this.questions.equals(lds.questions) && this.answers.equals(lds.answers) &&
				this.qcomments.equals(lds.qcomments) && this.acomments.equals(lds.acomments);
	}
	
}
