import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The superclass for all Page subclasses.
 * @author Yilin Gao
 *
 */
public class Page {
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	public static final String TITLE = "CellSociety";
	private CellSociety theCellSociety;
	private Stage stage;
	private Group root;
	private Scene scene;
	
	public Page (CellSociety cs){
		theCellSociety = cs;
		stage = cs.getStage();	
		stage.setTitle(TITLE);
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		stage.setScene(scene);
		stage.show();
	}
	
	public CellSociety getCellSociety(){
		return theCellSociety;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Group getRoot(){
		return root;
	}
	
	public Scene getScene(){
		return scene;
	}
	
	protected void readXMLInput(String path){
		
	}
}
