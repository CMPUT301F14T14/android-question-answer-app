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

	private static final String QUESTION_SAVE_FILE = "local_data_question.sav";
	private static final String ANSWER_SAVE_FILE = "local_data_answers.sav";
	private static final String QCOMMENT_SAVE_FILE = "local_data_qcomments.sav";
	private static final String ACOMMENT_SAVE_FILE = "local_data_acomments.sav";

	private Context context;
	private Map<UUID, Question> questions;
	private Map<UUID, Answer> answers;
	private Map<UUID, Comment<Question>> qcomments;
	private Map<UUID, Comment<Answer>> acomments;

	public LocalDataStore(Context context) {
		this.context = context;
		load();
	}

	public List<Question> getQuestionList(){
		return new ArrayList<Question>(questions.values());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This implementation will return all answers available locally.
	 * If they are not available locally, they will not be included.
	 */
	public List<Answer> getAnswerList(Question question) {
		List<Answer> list = new ArrayList<Answer>();
		for (UUID aId: question.getAnswerList()) {
			Answer a = getAnswer(aId);
			if (a != null) {
				list.add(a);
			}
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This implementation will return all comments available locally.
	 * If they are not available locally, they will not be included.
	 */
	public List<Comment<Question>> getCommentList(Question question) {
		List<Comment<Question>> list = new ArrayList<Comment<Question>>();
		for (UUID cId: question.getCommentList()) {
			Comment<Question> c = getQComment(cId);
			if (c != null) {
				list.add(c);
			}
		}
		return list;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * This implementation will return all comments available locally.
	 * If they are not available locally, they will not be included.
	 */
	public List<Comment<Answer>> getCommentList(Answer answer) {
		List<Comment<Answer>> list = new ArrayList<Comment<Answer>>();
		for (UUID cId: answer.getCommentList()) {
			Comment<Answer> c = getAComment(cId);
			if (c != null) {
				list.add(c);
			}
		}
		return list;
	}
	
	public void save() {
		saveObject(questions, QUESTION_SAVE_FILE);
		saveObject(answers, ANSWER_SAVE_FILE);
		saveObject(qcomments, QCOMMENT_SAVE_FILE);
		saveObject(acomments, ACOMMENT_SAVE_FILE);
	}

	private void saveObject(Object data, String saveFile) {
		OutputStream os;
		ObjectOutput oo;
		try {
			os = new BufferedOutputStream(context.openFileOutput(saveFile, Context.MODE_PRIVATE));
			oo = new ObjectOutputStream(os);
			try {
				oo.writeObject(data);
				Log.d("save", "Saved data to file " + saveFile);
			} finally {
				oo.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load serialized files.
	 * 
	 * This is horrible code.
	 */
	@SuppressWarnings("unchecked")
	private void load() {
		questions = (HashMap<UUID, Question>) loadObject(QUESTION_SAVE_FILE);
		answers = (HashMap<UUID, Answer>) loadObject(ANSWER_SAVE_FILE);
		qcomments = (HashMap<UUID, Comment<Question>>) loadObject(QCOMMENT_SAVE_FILE);
		acomments = (HashMap<UUID, Comment<Answer>>) loadObject(ACOMMENT_SAVE_FILE);
		if (questions == null)
			questions = new HashMap<UUID, Question>();
		if (answers == null)
			answers = new HashMap<UUID, Answer>();
		if (qcomments == null)
			qcomments = new HashMap<UUID, Comment<Question>>();
		if (acomments == null)
			acomments = new HashMap<UUID, Comment<Answer>>();
	}

	private Object loadObject(String saveFile) {
		InputStream is;
		ObjectInput oi;
		Object o;
		try {
			try {
				is = new BufferedInputStream(context.openFileInput(saveFile));
			} catch (FileNotFoundException e) {
				Log.d("load", "Serialized file " + saveFile + " not found.");
				return null;
			}
			oi = new ObjectInputStream(is);
			try {
				o = oi.readObject();
				if (o == null) {
					Log.e("load", "Loaded data was null.");
				} else {
					Log.d("load", "Successfully loaded data from " + saveFile);
				}
				return o;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				oi.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.e("load", "Error loading.");
		return null;
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

	/**
	 * Clear the internal data structures.
	 * 
	 * To persist this, you should call save() after.
	 */
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
