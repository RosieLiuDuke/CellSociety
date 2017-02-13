package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

/**
 * A class to display alerts for the program.
 * @author Yilin Gao
 */
public class DisplayAlert {

	/**
	 * The static method of DisplayAlert class to display an alert.
	 * @param prompt: alert information
	 */
	public static void displayAlert(String prompt){
		Alert alert = new Alert(AlertType.ERROR);
		Label label = new Label(prompt);
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);
		alert.showAndWait();
	}
}
