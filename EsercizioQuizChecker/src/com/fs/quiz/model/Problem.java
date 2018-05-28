package com.fs.quiz.model;

import java.util.UUID;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The problem proposed to the user. The answer is stored in one of its attributes.
 * Has a unique ID to help identification
 * @author FS
 *
 */
public class Problem {
	
	private final int id;
	
	// dynamic fields that are shown in the UI
	private final StringProperty title;
	private final StringProperty problemText;
	private final DoubleProperty answer;
	
	/**
	 * Basic constructor, automatically sets up the unique ID.
	 * @param title the title of the problem, shown in the listview
	 * @param text the actual question to be solved
	 * @param answer the answer to the problem, not seen by the user
	 */
	public Problem(String title, String text, double answer) {
		this.title = new SimpleStringProperty(title);
		this.problemText = new SimpleStringProperty(text);
		this.answer = new SimpleDoubleProperty(answer);
		this.id = UUID.randomUUID().hashCode();
	}

	public StringProperty getTitle() {
		return title;
	}

	public StringProperty getProblemText() {
		return problemText;
	}

	public DoubleProperty getAnswer() {
		return answer;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return title.get() + "," + problemText.get() + "," + answer.get();
	}
}
