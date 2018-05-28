package com.fs.quiz.view;

import com.fs.quiz.Utils;
import com.fs.quiz.model.ProblemModel;
import com.fs.quiz.model.Problem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the dialog used to insert a new problem.
 * @author FS
 *
 */
public class NewProblemDialogController {

	private ProblemModel model;

	@FXML
	private AnchorPane root;

	@FXML
	private TextField newProblemTitle;

	@FXML
	private TextArea newProblemText;

	@FXML
	private TextField newProblemAnswer;
	
	/**
	 * Hooks up the model.
	 * @param model
	 */
	public void initModel(ProblemModel model) {
		if (this.model != null) {
			throw new IllegalStateException("Model can only be initialized once");
		}
		this.model = model;
	}

	/**
	 * Closes the dialog.
	 */
	@FXML
	private void close() {
		root.getScene().getWindow().hide();
	}

	/**
	 * Saves the newly insert problem in the problem list (still needs to be saved to file
	 * in order to have it persisted).
	 */
	@FXML
	private void save() {
		try {
			String problemTitle = newProblemTitle.getText();
			String problemText = newProblemText.getText();
			String answerText = newProblemAnswer.getText();

			if (problemTitle.isEmpty() || problemText.isEmpty() || answerText.isEmpty()) {
				throw new java.lang.IllegalStateException();
			}

			model.getProblemList().add(new Problem(problemTitle, problemText, Double.parseDouble(answerText)));
			close();
		} catch (IllegalStateException e) {
			Utils.showDialog(AlertType.WARNING, "Please fill in all fields.", "All fields must be filled in.");
		} catch (NumberFormatException e) {
			Utils.showDialog(AlertType.WARNING, "Invalid answer", "Invalid answer: answer must be a number.");
		}
	}

}
