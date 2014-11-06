package ca.ualberta.cs.cmput301f14t14.questionapp.test.mock;

import java.util.ArrayList;
import java.util.Collection;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class MockData {
	public static Collection<Question> getMockData() {
		Collection<Question> data = new ArrayList<Question>();

		Question q1 = new Question("Coconut laden swallow", "What is the terminal velocity of a coconut laden swallow?", "Boris", null);
		q1.addComment(new Comment<Question>(q1, "What kind of swallow is it?", "Bob"));
		q1.addComment(new Comment<Question>(q1, "An african swallow.", "Boris"));
		Answer a1 = new Answer(q1, "The terminal velocity is 42.", "Curtis", null);
		a1.addComment(new Comment<Answer>(a1, "The units are furlongs per fortnight.", "Curtis"));
		Answer a2 = new Answer(q1, "It's clearly 45m/s.", "Bjorn", null);
		q1.addAnswer(a1);
		q1.addAnswer(a2);
		data.add(q1);

		Question q2 = new Question("Why is the sky blue?", "Really, why is it blue!?", "Pearl", null);
		q2.addComment(new Comment<Question>(q2, "Excellent use of an interrobang!", "Ben"));
		data.add(q2);

		Question q3 = new Question("RxJava and C# Threading", "How similar are RxJava and C#'s threading library?", "Ted", null);
		q3.addAnswer(new Answer(q3, "This is for you to discover, young Padawaan.", "Master Yoda", null));
		data.add(q3);

		Question validQ = new Question("TITLE", "BODY", "AUTHOR", null);
		Answer validA = new Answer(validQ, "aBody", "aAuthor", null);
		validQ.addAnswer(validA);
		data.add(validQ);
		
		return data;
	}
}
