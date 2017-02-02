import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
* This is the Main file which launches the Cell Society Program
* @author Harry Liu, Josh Kopen, and Yilin Gao
**/

public class Main extends Application {
	
	private CellSociety cellSociety;
	
	
	@Override
	public void start(Stage theStage){
		cellSociety = new CellSociety(theStage);
		cellSociety.initializeSimulationPage("Game of Life");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}