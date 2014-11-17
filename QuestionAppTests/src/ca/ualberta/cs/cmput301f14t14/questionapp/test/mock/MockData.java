package ca.ualberta.cs.cmput301f14t14.questionapp.test.mock;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class MockData {
	public static List<Question> questions;
	public static List<Answer> answers;
	public static List<Comment<Question>> qcomments;
	public static List<Comment<Answer>> acomments;
	
	public static void initMockData() {
		questions = new ArrayList<Question>();
		answers = new ArrayList<Answer>();
		qcomments = new ArrayList<Comment<Question>>();
		acomments = new ArrayList<Comment<Answer>>();

		Question q1 = new Question("Coconut laden swallow", "What is the terminal velocity of a coconut laden swallow?", "Boris", null);
		Comment<Question> cq1 = new Comment<Question>(q1.getId(), "What kind of swallow is it?", "Bob");
		q1.addComment(cq1.getId());
		qcomments.add(cq1);
		Comment<Question> cq2 = new Comment<Question>(q1.getId(), "An african swallow.", "Boris");
		q1.addComment(cq2.getId());
		qcomments.add(cq2);
		Answer a1 = new Answer(q1.getId(), "The terminal velocity is 42.", "Curtis", null);
		Comment<Answer> aq1 = new Comment<Answer>(a1.getId(), "The units are furlongs per fortnight.", "Curtis");
		a1.addComment(aq1);
		acomments.add(aq1);
		answers.add(a1);
		Answer a2 = new Answer(q1.getId(), "It's clearly 45m/s.", "Bjorn", null);
		answers.add(a2);
		q1.addAnswer(a1.getId());
		q1.addAnswer(a2.getId());
		questions.add(q1);

		Question q2 = new Question("Why is the sky blue?", "Really, why is it blue!?", "Pearl", null);
		Comment<Question>cq3 = new Comment<Question>(q2.getId(), "Excellent use of an interrobang!", "Ben");
		q2.addComment(cq3.getId());
		questions.add(q2);
		qcomments.add(cq3);

		Question q3 = new Question("RxJava and C# Threading", "How similar are RxJava and C#'s threading library?", "Ted", null);
		Answer a3 = new Answer(q3.getId(), "This is for you to discover, young Padawaan.", "Master Yoda", null);
		q3.addAnswer(a3.getId());
		questions.add(q3);
		answers.add(a3);

		Question validQ = new Question("TITLE", "BODY", "AUTHOR", null);
		Answer validA = new Answer(validQ.getId(), "aBody", "aAuthor", null);
		validQ.addAnswer(validA.getId());
		questions.add(validQ);
		answers.add(validA);
	}
}
