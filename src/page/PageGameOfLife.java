package page;

import cellSociety.CellSociety;

/**
 * The subclass Page to hold the Scene for the Game of Life simulation.
 * @author Yilin Gao
 * @author Harry Liu
 */
public class PageGameOfLife extends UIsetup {

	/**
	 * Constructor of the page of Game of Life simulation
	 * @param cs: the CellSociety instance
	 * @param language: a string representing user choice of language
	 * @param p: a Parameters instant from the calling class
	 */
	public PageGameOfLife(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
		
	/**
	 * The method to set up required components in the scene.
	 * For game of life, the method in the super class is enough.
	 */
	@Override
	protected void setupComponents(){
		super.setupComponents();
	}
	
	/**
	 * The method to set up the grid layout in the scene.
	 * The method calls the super class method as its whole function.
	 * If more coded layouts are expected, they can be placed in this method in the future.
	 * @param newValue
	 */
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		// can add other layouts
	}
	
	/**
	 * The method to update the parameters displayed at the top of the UI Screen.
	 * For game of life, the method in the super class is enough.
	 */
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
	}
}