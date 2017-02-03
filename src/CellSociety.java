import java.util.Hashtable;

import javafx.stage.Stage;

public class CellSociety {
	private Stage stage;
	private Hashtable<String, Page> pages = new Hashtable<>();
	private Hashtable<String, Animation> animations = new Hashtable<>();
	
	public Stage getStage(){
		return stage;
	}
	
	public CellSociety(Stage theStage) {
		// TODO Auto-generated constructor stub
		stage = theStage;
	}
	
	public void initializeWelcomePage(){
		Page newWelcomePage = new WelcomePage(this);
		pages.put("Welcome", newWelcomePage);
	}
	
	public void initializeSimulationPage(String type){
		if (type.equals("Game of Life")){
			Page newGameOfLifePage = new PageGameOfLife(this);
			pages.put(type, newGameOfLifePage);
			Animation newGameOfLifeAnimation = new GameOfLife(this);
			animations.put(type, newGameOfLifeAnimation);
		}
	}
	
	

}
