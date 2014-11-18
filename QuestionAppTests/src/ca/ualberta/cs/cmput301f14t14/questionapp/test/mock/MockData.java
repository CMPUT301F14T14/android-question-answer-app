package ca.ualberta.cs.cmput301f14t14.questionapp.test.mock;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class MockData {
	public static List<Question> getMockData() {
		List<Question> data = new ArrayList<Question>();

		Question q1 = new Question("Coconut laden swallow", "What is the terminal velocity of a coconut laden swallow?", "Boris", null);
		Comment<Question> cq1 = new Comment<Question>(q1.getId(), "What kind of swallow is it?", "Bob");
		q1.addComment(cq1.getId());
		Comment<Question> cq2 = new Comment<Question>(q1.getId(), "An african swallow.", "Boris");
		q1.addComment(cq2.getId());
		Answer a1 = new Answer(q1.getId(), "The terminal velocity is 42.", "Curtis", null);
		a1.addComment(new Comment<Answer>(a1.getId(), "The units are furlongs per fortnight.", "Curtis"));
		Answer a2 = new Answer(q1.getId(), "It's clearly 45m/s.", "Bjorn", null);
		q1.addAnswer(a1.getId());
		q1.addAnswer(a2.getId());
		data.add(q1);

		Question q2 = new Question("Why is the sky blue?", "Really, why is it blue!?", "Pearl", null);
		Comment<Question >cq3 = new Comment<Question>(q2.getId(), "Excellent use of an interrobang!", "Ben");
		q2.addComment(cq3.getId());
		data.add(q2);

		Question q3 = new Question("RxJava and C# Threading", "How similar are RxJava and C#'s threading library?", "Ted", null);
		Answer a3 = new Answer(q3.getId(), "This is for you to discover, young Padawaan.", "Master Yoda", null);
		q3.addAnswer(a3.getId());
		data.add(q3);

		Question validQ = new Question("TITLE", "BODY", "AUTHOR", null);
		Answer validA = new Answer(validQ.getId(), "aBody", "aAuthor", null);
		validQ.addAnswer(validA.getId());
		data.add(validQ);
		
		return data;
	}
}
