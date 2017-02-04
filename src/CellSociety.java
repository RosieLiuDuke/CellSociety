import java.io.File;
import java.util.Hashtable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CellSociety {
	private Stage stage;
	private File inputFile;
	private Hashtable<String, Page> pages = new Hashtable<>();
	private Hashtable<String, Animation> animations = new Hashtable<>();
	private String currentType = "";
	private String nextType = "";
	
	private Timeline timeline; 
	private double millisecondDelay;
	private double secondDelay;	
	
	// if the simulation is step by step or consecutive. Default is step by step.
	private Boolean isStep = false; 
	private Boolean nextStep = false;
		
	public Stage getStage(){
		return stage;
	}
	
	public File getFile(){
		return inputFile;
	}
	
	public Page getPage(String type){
		return pages.get(type);
	}
	
	public String getCurrentType(){
		return currentType;
	}
	
	public String getNextType(){
		return nextType;
	}
	
	public void setFile(File file){
		inputFile = file;
	}
	
	public void setDelay(double step){
		millisecondDelay = 1000 * step;
		secondDelay = step;
	}
	
	public void setIsStep(Boolean value){
		isStep = value;
	}
	
	public void setNextStep(Boolean value){
		nextStep = value;
	}
	
	public void setCurrrentType(String s){
		currentType = s;
	}
	
	public void setNextType(String s){
		nextType = s;
	}
	
	public CellSociety(Stage theStage) {
		stage = theStage;
	}
	
	public void initializePage(String type){
		currentType = type;
		if (type.equals("Welcome")){
			Page newWelcomePage = new WelcomePage(this);
			pages.put(type, newWelcomePage);			
		}
		else if (type.equals("Game of Life")){
			Page newGameOfLifePage = new PageGameOfLife(this);
			pages.put(type, newGameOfLifePage);
			Animation newGameOfLifeAnimation = new AnimationGameOfLife(this);
			animations.put(type, newGameOfLifeAnimation);
		}
	}
	
	public void loadPage(String type){
		pages.clear();
		animations.clear();
		initializePage("Welcome");
	}
	
	public void setupGameLoop(){
		Duration oneFrameDuration = Duration.millis(millisecondDelay);
		KeyFrame oneFrame = new KeyFrame(oneFrameDuration, new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				actionsPerFrame(secondDelay);
			}
			
		});
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(oneFrame);
	}
	
	public void beginGameLoop(){
		timeline.play();
	}
	
	public void stopGameLoop(){
		timeline.stop();
	}

	private void actionsPerFrame(double elapsedTime) {
		// TODO Auto-generated method stub
		// if the current mode is consecutive simulation
		if (!isStep){
			animations.get(currentType).calculateMove();
			((PageGameOfLife) pages.get(currentType)).updateColor();
		}
		else {
			if (nextStep){
				animations.get(currentType).calculateMove();
				((PageGameOfLife) pages.get(currentType)).updateColor();
				nextStep = false;
			}
		}
	}
}
