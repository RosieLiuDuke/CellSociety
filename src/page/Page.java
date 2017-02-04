package page;
import cellSociety.CellSociety;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.XMLReader;
/**
 * The superclass for all Page subclasses.
 * @author Yilin Gao, Harry Liu
 *
 */
public class Page {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "CellSociety";
	private CellSociety theCellSociety;
	private Stage stage;
	private BorderPane root;
	private Scene scene;
	private XMLReader xmlReader;
	
	/**
	 * The constructor of the Page class. 
	 * @param cs
	 */
	public Page (CellSociety cs){
		theCellSociety = cs;
		stage = cs.getStage();	
		stage.setTitle(TITLE);
		root = new BorderPane();
		scene = new Scene(root, WIDTH, HEIGHT);
		xmlReader = new XMLReader(theCellSociety);
	}
	
	/**
	 * The method to set the current scene to the stage, and show the stage.
	 */
	public void showPage(){
		stage.setScene(scene);
		stage.show();
	}
	
	public CellSociety getCellSociety(){
		return theCellSociety;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public BorderPane getRoot(){
		return root;
	}
	
	public Scene getScene(){
		return scene;
	}
	
	public XMLReader getXMLReader(){
		return xmlReader;
	}
	
	/**
	 * A general method to create any buttons.
	 * @param name: button name
	 * @param handler: EventHandler of the button
	 * @return
	 */
	public Button createButton (String name, EventHandler<ActionEvent> handler ) {
		Button newButton = new Button(name);
		newButton.setOnAction(handler);
		return newButton;
	}
}