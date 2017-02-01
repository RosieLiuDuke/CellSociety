import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * The superclass for all Page subclasses.
 * @author Yilin Gao
 *
 */
public class Page {
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 600;
	private Group root;
	private Scene scene;
	
	public Page (){
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
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
