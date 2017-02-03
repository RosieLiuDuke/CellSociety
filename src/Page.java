import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The superclass for all Page subclasses.
 * @author Yilin Gao
 *
 */
public abstract class Page {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	public static final String TITLE = "CellSociety";
	private Stage stage;
	private Group root;
	private Scene scene;

	public Page (Stage theStage){
		stage = theStage;
		stage.setTitle(TITLE);
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		stage.setScene(scene);
		stage.show();
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

	public abstract void initializePage();

	protected void readXMLInput(String path){

	}
}
