import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
* This is the Main file which launches the Cell Society Program
* @author Harry Liu, Josh Kopen, and Yilin Gao
**/

public class Main extends Application {
	
	private Stage myStage;
	private static final String TITLE = "CellSociety";
	
	
	@Override
	public void start(Stage theStage){
		myStage = theStage;
		theStage.setTitle( TITLE );
		
		welcomePage welcomePage = new welcomePage();
		
		theStage.setResizable(false); 
		theStage.setScene( welcomePage.getScene() );
		theStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}