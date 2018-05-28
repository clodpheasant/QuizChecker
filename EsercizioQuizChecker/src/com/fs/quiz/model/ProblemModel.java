package com.fs.quiz.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fs.quiz.QuizApp;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The model handles setting up the data and giving it to the controller, to be shown in the UI.
 * @author FS
 *
 */
public class ProblemModel {
	private URI currFilePath;
	
	private final ObservableList<Problem> problemList = FXCollections.observableArrayList(
			problem -> new Observable[] { problem.getTitle(), problem.getProblemText(), problem.getAnswer() });
	
	private final ObjectProperty<Problem> currentProblem = new SimpleObjectProperty<>(null);

	public ObjectProperty<Problem> currentProblemProperty() {
		return currentProblem;
	}
	
	/**
	 * @return the problem that's currently selected.
	 */
	public final Problem getCurrentProblem() {
		return currentProblemProperty().get();
	}
	
	/**
	 * @param the problem that has to be set as current.
	 */
	public final void setCurrentProblem(Problem problem) {
		currentProblemProperty().set(problem);
	}
	
	/**
	 * @return the list of all the problems.
	 */
	public ObservableList<Problem> getProblemList() {
		return problemList;
	}
	
	/**
	 * Loads problems from a file
	 * @param path URI containing the path of the file to be loaded
	 */
	public void loadData(URI path) {
		try {
			List<Problem> loadProblems = new ArrayList<>();
			
			currFilePath = (path == null)
					? QuizApp.class.getResource("/default.txt").toURI()
					: path;
					
			loadProblems = Files.lines(Paths.get(currFilePath))
					.map(x -> x.split(","))
					.map(x -> new Problem(x[0], x[1], Double.parseDouble(x[2])))
					.collect(Collectors.toList());
			
			// mock data, the problems are hard-coded
			/*problemList.setAll(new Problem("problem 1", "How much is 2 + 2?", 4),
					new Problem("problem 2", "How much is 5 / 2?", 2.5),
					new Problem("problem 3", "What is the square root of 4?", 2),
					new Problem("problem 4", "How much is 3 squared?", 9)
			);*/
			
			problemList.setAll(loadProblems);
		} catch (URISyntaxException|IOException|NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public URI getCurrFilePath() {
		return currFilePath;
	}
}
