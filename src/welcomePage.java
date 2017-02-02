import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class welcomePage extends Page {
	private Scene myScene;
	private Stage theStage;
	private String BACKGROUND = "splash_bg.jpg";
	private Button UPLOAD = new Button("UPLOAD FILE");
	private Button START = new Button ("START SIMULATION");
	
	@Override
	public Scene getScene(){
		return myScene;
	}
	
	public welcomePage(){
		Scene theScene = new Scene( mainmenu() );
		theScene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());
		myScene = theScene;
	}

	private Parent mainmenu() {
		StackPane root = new StackPane();
		root.setPrefSize(super.WIDTH, super.HEIGHT);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND));
		ImageView iV = new ImageView();
		iV.setImage(image);
		iV.setFitWidth(super.WIDTH);
		iV.setFitHeight(super.HEIGHT);

		Text title = new Text ("Cell Society");
		title.setId("title");
		title.setTranslateY(-super.HEIGHT/5);
		
		UPLOAD.setMaxWidth(super.WIDTH/2);
		START.setMaxWidth(super.WIDTH/2);

		VBox myMenu = new VBox(10);
		myMenu.setPadding(new Insets(1));
		myMenu.getChildren().addAll(UPLOAD, START);
		myMenu.setAlignment(Pos.CENTER);

		handleMouseHover();

		START.setOnMouseClicked(event -> {
			

		});

		UPLOAD.setOnMouseClicked(event -> {
			
			XMLReader reader = new XMLReader();
			try {
				reader.start(theStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		root.getChildren().addAll(iV, title, myMenu);
		return root;
	}
	
	/**
	 * Handle Mouse Hover Effects for Buttons
	 **/
	private void handleMouseHover(){
		//MOUSE ENTER
		START.setOnMouseEntered(event -> {
			START.setEffect(shadow());
		});

		UPLOAD.setOnMouseEntered(event -> {
			UPLOAD.setEffect(shadow());
		});

		//MOUSE EXIT
		START.setOnMouseExited(event -> {
			START.setEffect(null);
		});
		
		UPLOAD.setOnMouseExited(event -> {
			UPLOAD.setEffect(null);
		});
	}
	
	/**
	 * Create drop shadow effect used by buttons
	 * @return shadow
	 **/
	private Effect shadow(){
		DropShadow shadow = new DropShadow();
		shadow.setColor(Color.BEIGE);
		return shadow;
	}
}