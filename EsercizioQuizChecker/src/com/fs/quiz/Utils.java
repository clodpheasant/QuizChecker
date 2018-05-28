package com.fs.quiz;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Various utility functions used throughout the app
 * @author FS
 *
 */
public class Utils {
	
	/**
	 * Helper method to set up the dialog box.
	 * @param alertType type of the dialog, influences its appearance
	 * @param title the title of the dialog box
	 * @param message the message to be shown in the dialog box
	 */
	public static void showDialog(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(message);

		alert.showAndWait();
	}

}
