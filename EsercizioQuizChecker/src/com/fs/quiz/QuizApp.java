package com.fs.quiz;

import java.io.IOException;
import java.net.URISyntaxException;

import com.fs.quiz.model.ProblemModel;
import com.fs.quiz.view.MenuBarController;
import com.fs.quiz.view.NewProblemDialogController;
import com.fs.quiz.view.ProblemOverviewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * App that shows mathematical quizzes and verifies the answers given by the
 * user.
 * 
 * @author FS
 *
 */
public class QuizApp extends Application {
	
	private ProblemModel model;

	@Override
	public void start(Stage primaryStage) throws IOException, URISyntaxException {
		// setting up the stage
		primaryStage.setTitle("Quiz Checker");

		// root to attach the various elements of the UI
		BorderPane root = new BorderPane();

		// loading the FXML main view
		FXMLLoader problemLoader = new FXMLLoader();
		problemLoader.setLocation(QuizApp.class.getResource("view/ProblemOverview.fxml"));
		root.setCenter(problemLoader.load());

		// loading the FXML menu bar
		FXMLLoader menuLoader = new FXMLLoader();
		menuLoader.setLocation(QuizApp.class.getResource("view/MenuBar.fxml"));
		root.setTop(menuLoader.load());

		// getting the controllers and hooking up the model
		ProblemOverviewController problemController = problemLoader.getController();
		MenuBarController menuBarController = menuLoader.getController();
		menuBarController.setMainApp(this);

		model = new ProblemModel();
		model.loadData(null);
		problemController.initModel(model);
		menuBarController.initModel(model);

		// setting up the scene
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Shows the dialog used to save a new problem
	 * @throws IOException
	 */
	public void showNewProblemDialog() throws IOException {
		FXMLLoader newProblemLoader = new FXMLLoader();
		newProblemLoader.setLocation(QuizApp.class.getResource("view/NewProblemDialog.fxml"));
		AnchorPane dialog = (AnchorPane) newProblemLoader.load();

		Stage dialogStage = new Stage();
		dialogStage.setTitle("Add Problem");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(dialog);
		dialogStage.setScene(scene);

		NewProblemDialogController controller = newProblemLoader.getController();
		controller.initModel(model);
		
		dialogStage.showAndWait();
	}
}
