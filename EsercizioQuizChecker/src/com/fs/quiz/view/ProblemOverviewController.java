package com.fs.quiz.view;

import java.util.Random;

import com.fs.quiz.Utils;
import com.fs.quiz.model.ProblemModel;
import com.fs.quiz.model.Problem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * Handles the data provided by the model and shows it on the UI of the main app window.
 * Updates the UI according to the user's actions.
 * @author FS
 *
 */
public class ProblemOverviewController {

	@FXML
	private ListView<Problem> problemList;

	@FXML
	private TextArea problemText;

	@FXML
	private TextArea answerText;

	private ProblemModel model;
	
	private Random r;
	
	/**
	 * Hooks up the model and sets up listener
	 * @param model
	 */
	public void initModel(ProblemModel model) {
		// ensure model is only set once:
		if (this.model != null) {
			throw new IllegalStateException("Model can only be initialized once");
		}

		this.model = model;
		// loads the data from the model into the listview
		problemList.setItems(model.getProblemList());
		
		// listener to react to the selection of a new problem
		problemList.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> model.setCurrentProblem(newSelection));
		
		// listener to get the properties of the newly selected problem and showing them on the UI
		model.currentProblemProperty().addListener((obs, oldProblem, newProblem) -> {
			if (newProblem == null) {
				problemList.getSelectionModel().clearSelection();
			} else {
				problemList.getSelectionModel().select(newProblem);
				problemText.setText(newProblem.getProblemText().get());
			}
		});
		
		// sets up the listview
		problemList.setCellFactory(lv -> new ListCell<Problem>() {
			@Override
			public void updateItem(Problem problem, boolean empty) {
				super.updateItem(problem, empty);
				if (empty) {
					setText(null);
				} else {
					setText(problem.getTitle().get());
				}
			}
		});
		
		// initialises the random number generator
		r = new Random();
	}

	/**
	 * Checks that the answer provided by the user is correct and shows a dialog with the outcome.
	 */
	@FXML
	private void handleAnswerClick() {
		AlertType alertType = AlertType.WARNING;
		String dialogTitle;
		String dialogMessage;
		
		// gets the problem that's been selected
		Problem selectedProblem = problemList.getSelectionModel().getSelectedItem();
		
		// checks to see whether an answer has beeen selected and if it's correct
		if (selectedProblem != null) {
			String answer = answerText.getText();
			if (answer != null && !answer.isEmpty()) {
				try {
					double ans = Double.parseDouble(answer);
					if (ans != selectedProblem.getAnswer().get()) {
						alertType = AlertType.ERROR;
						dialogTitle = "Wrong answer!";
						dialogMessage = "Wrong answer! Please try again.";
					} else {
						alertType = AlertType.INFORMATION;
						dialogTitle = "Correct answer!";
						dialogMessage = "Correct answer! Good job :)";
					}
				} catch (NumberFormatException e) {
					answerText.setText("");
					dialogTitle = "Invalid answer";
					dialogMessage = "Invalid answer: answer must be a number.";
				}
			} else {
				dialogTitle = "No answer given";
				dialogMessage = "Please write an answer in the text area.";
			}
		} else {
			dialogTitle = "No problem selected";
			dialogMessage = "Please select a problem from the list.";
		}

		Utils.showDialog(alertType, dialogTitle, dialogMessage);
	}
	
	/**
	 * sets the current problem with a random one (different from the current one)
	 */
	@FXML
	private void getRandomProblem() {
		int numOfProblems = model.getProblemList().size();
		int randIndex = r.nextInt(numOfProblems);
		Problem randProblem = model.getProblemList().get(randIndex);
		Problem currProblem = model.getCurrentProblem();
		
		// if the problem is the same as the one that's been currently selected i get the next one
		// this makes getting the next problem on the slightly more probable than the others
		if (currProblem != null && randProblem.getId() == currProblem.getId()) {
			randProblem = model.getProblemList().get((randIndex + 1) % numOfProblems);
		}
		
		// sets current problem and wipes answer
		model.setCurrentProblem(randProblem);
		answerText.setText("");;
	}
}
