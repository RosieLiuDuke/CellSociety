import cellSociety.CellSociety;
import javafx.application.Application;
import javafx.stage.Stage;

/**
* This is the Main file which launches the Cell Society Program
* @author Harry Liu
* @author Josh Kopen
* @author Yilin Gao
**/

public class Main extends Application {

	@Override
	public void start(Stage theStage){
		new CellSociety(theStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
