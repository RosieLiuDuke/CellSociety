import java.util.ArrayList;

import javafx.stage.Stage;

public class CellSociety {
	private Stage stage;
	private ArrayList<Page> pages = new ArrayList<>();
	private ArrayList<Animation> animations = new ArrayList<>();
	

	public CellSociety(Stage theStage) {
		// TODO Auto-generated constructor stub
		stage = theStage;
	}
	
	public void initializeWelcomePage(){
		
	}
	
	public void initializeSimulationPage(int type){
		if (type == 0){
			Page newGameOfLifePage = new PageGameOfLife(stage);
			pages.add(newGameOfLifePage);
			Animation newGameOfLifeAnimation = new GameOfLife(this);
			animations.add(newGameOfLifeAnimation);
		}
	}
	
	

}
