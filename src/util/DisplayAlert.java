package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class DisplayAlert {

	public static void displayAlert(String prompt){
		Alert alert = new Alert(AlertType.ERROR);
		Label label = new Label(prompt);
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);
		alert.showAndWait();
	}
}
