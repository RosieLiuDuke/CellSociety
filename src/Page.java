import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * The superclass for all Page subclasses.
 * @author Yilin Gao, Harry Liu
 *
 */
public class Page {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final String TITLE = "CellSociety";
	private CellSociety theCellSociety;
	private Stage stage;
	private BorderPane root;
	private Scene scene;
	private XMLReader xmlReader;
	
	public Page (CellSociety cs){
		theCellSociety = cs;
		stage = cs.getStage();	
		stage.setTitle(TITLE);
		root = new BorderPane();
		scene = new Scene(root, WIDTH, HEIGHT);
		xmlReader = new XMLReader(theCellSociety);
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
	
	protected void readXMLInput(String path){
		
	}
	
	public Button createButton (String name, EventHandler<ActionEvent> handler ) {
		Button newButton = new Button(name);
		newButton.setOnAction(handler);
		return newButton;
	}
}