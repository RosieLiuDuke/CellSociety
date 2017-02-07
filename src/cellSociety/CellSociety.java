package cellSociety;
import java.io.File;
import java.util.Hashtable;

import animation.Animation;
import animation.AnimationGameOfLife;
import animation.AnimationSegregation;
//import animation.AnimationSegregation;
import animation.AnimationSpreadingOfFire;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import page.GamePage;
import page.Page;
import page.PageGameOfLife;
import page.PageSegregation;
import page.PageSpreadingOfFire;
import page.WelcomePage;

/**
 * The class to maintain pages, animations and the game loop.
 * @author Yilin Gao
 *
 */
public class CellSociety {
	private Stage stage;
	private File inputFile;
	private Hashtable<String, Page> pages = new Hashtable<>();
	private Hashtable<String, Animation> animations = new Hashtable<>();
	private String currentType = "";
	private String nextType = "";
	
	private Timeline timeline; 
	private double millisecondDelay;
	
	// if the simulation is step by step or consecutive.
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
	
	public Animation getAnimation(String type){
		return animations.get(type);
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
	
	public void setCurrrentType(String s){
		currentType = s;
	}
	
	public void setNextType(String s){
		nextType = s;
	}
	
	public void setDelay(double step){
		millisecondDelay = 1000 / step;
	}
	
	public void setIsStep(Boolean value){
		isStep = value;
	}
	
	public void setNextStep(Boolean value){
		nextStep = value;
	}
	
	/**
	 * The constructor of the CellSociety class.
	 * @param theStage
	 */
	public CellSociety(Stage theStage) {
		stage = theStage;
	}
	
	public void initializePage(String type){
		currentType = type;
		if (type.equals("Welcome")){
			Page newWelcomePage = new WelcomePage(this);
			pages.put(type, newWelcomePage);
			newWelcomePage.showPage();
		}
		else if (type.equals("Game of Life")){
			Page newGameOfLifePage = new PageGameOfLife(this);
			pages.put(type, newGameOfLifePage);
			Animation newGameOfLifeAnimation = new AnimationGameOfLife(this);
			animations.put(type, newGameOfLifeAnimation);
		}
		else if (type.equals("Segregation")){
			Page newSegregationPage = new PageSegregation(this);
			pages.put(type, newSegregationPage);
			// TODO
			Animation newSegregationAnimation = new AnimationSegregation(this); 
			animations.put(type, newSegregationAnimation);
		}
		else if (type.equals("Fire")){
			Page newFirePage = new PageSpreadingOfFire(this);
			pages.put(type, newFirePage);
			Animation newFireAnimation = new AnimationSpreadingOfFire(this);
			animations.put(type, newFireAnimation);
		}
	}
	
	public void loadPage(String type){
		pages.clear();
		animations.clear();
		inputFile = null;
		nextType = "";
		initializePage(type);
	}
	
	public void setupGameLoop(){
		Duration oneFrameDuration = Duration.millis(millisecondDelay);
		KeyFrame oneFrame = new KeyFrame(oneFrameDuration, new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				actionsPerFrame();
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

	private void actionsPerFrame() {
		// if the current mode is consecutive simulation
		if (!isStep){
			animations.get(currentType).calculateMove();
			((GamePage)pages.get(currentType)).setCurrentStep(((GamePage)pages.get(currentType)).getCurrentStep() + 1);
			((GamePage) pages.get(currentType)).updateColor();
			((GamePage) pages.get(currentType)).updateTextInfo();
			((GamePage) pages.get(currentType)).addTextInfo();
		}
		// if the current mode is simulation step by step
		else {
			if (nextStep){
				animations.get(currentType).calculateMove();
				((GamePage)pages.get(currentType)).setCurrentStep(((GamePage)pages.get(currentType)).getCurrentStep() + 1);
				((GamePage) pages.get(currentType)).updateColor();
				((GamePage) pages.get(currentType)).updateTextInfo();
				nextStep = false;
			}
		}
	}
}
