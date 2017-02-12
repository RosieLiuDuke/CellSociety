package page;
import java.util.ResourceBundle;
import cellSociety.CellSociety;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.XMLReader;
/**
 * The superclass for all Page subclasses.
 * @author Yilin Gao, Harry Liu
 *
 */
public class Page {
	
	protected static final int WIDTH = 800;
	protected static final int HEIGHT = 600;
	private static final String TITLE = "CellSociety";
	private CellSociety theCellSociety;
	private Stage stage;
	private BorderPane root;
	private Scene scene;
	private XMLReader xmlReader;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private String myLanguage;
	private ResourceBundle myResourceBundle;
	private Parameters parametersController;
	
	/**
	 * The constructor of the Page class. 
	 * @param cs
	 */
	public Page (CellSociety cs, String language){
		theCellSociety = cs;
		stage = cs.getStage();	
		stage.setTitle(TITLE);
		root = new BorderPane();
		scene = new Scene(root, WIDTH, HEIGHT);
		xmlReader = new XMLReader(theCellSociety);
		myLanguage = language;
		myResourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		parametersController = new Parameters();
	}

	public Parameters getParametersController(){
		return parametersController;
	}
	
	public void setParametersController(Parameters p){
		parametersController = p;
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
	
	public String getLanguage(){
		return myLanguage;
	}
	
	public ResourceBundle getResourceBundle(){
		return getMyResources();
	}
	
	public void setMyResources(String newLang) {
		myLanguage = newLang;
	}
	
	public ResourceBundle getMyResources() {
		return myResourceBundle;
	}
	
	public int getWidth(){
		return WIDTH;
	}
	
	public int getHeight(){
		return HEIGHT;
	}
	
	public void changeLanguage(String newLang){
		setMyResources(newLang);
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
	
	public void displayAlert(String prompt){
		Alert alert = new Alert(AlertType.ERROR, prompt);
		alert.showAndWait();
	}
}