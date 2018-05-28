package com.fs.quiz.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.fs.quiz.QuizApp;
import com.fs.quiz.model.ProblemModel;
import com.fs.quiz.model.Problem;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

/**
 * Controller for the menu bar. Handles saving, loading, quitting and adding problems.
 * @author FS
 *
 */
public class MenuBarController {
	
	private ProblemModel model;
	private QuizApp mainApp;
	
	@FXML
	private MenuBar menuBar;
	
	/**
	 * Hooks up the model.
	 * @param model
	 */
	public void initModel(ProblemModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model ;
    }
	
	/**
	 * Saves the current list of problems to file. The file to be saved on
	 * is the same from which the list is read.
	 */
	@FXML
	private void save() {
		List<Problem> toBeSaved = model.getProblemList();
		File file = new File(model.getCurrFilePath());
		System.out.println(model.getCurrFilePath());
		
		try (FileWriter writer = new FileWriter(file)) {
			for (Problem p : toBeSaved) {
				writer.write(p.toString() + "\n");
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls the model load a list of problems from file.
	 */
	@FXML
	private void load() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {
            try {
                model.loadData(file.toURI());
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
	}
	
	/**
	 * Closes the app.
	 */
	@FXML
	private void quit() {
		menuBar.getScene().getWindow().hide();
	}
	
	/**
	 * Calls the main app to open the dialog used to insert a new problem
	 * @throws IOException
	 */
	@FXML
	private void showNewProblemDialog() throws IOException {
		mainApp.showNewProblemDialog();
	}
	
	public void setMainApp(QuizApp mainApp) {
		this.mainApp = mainApp;
	}

}
