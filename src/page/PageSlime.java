package page;

import cellSociety.CellSociety;

/**
 * The subclass Page to hold the Scene for the slimer simulation.
 * @author Josh Kopen
 *
 */
public class PageSlime extends UIsetupWithPercentage{
	
	/**
	 * Constructor of the page of slime simulation
	 * @param cs: the CellSociety instance
	 * @param language: a string representing user choice of language
	 * @param p: a Parameters instant from the calling class
	 */
	public PageSlime(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}

	/**
	 * The method to set up required components in the scene.
	 */
	@Override
	protected void setupComponents(){
		super.setupComponents();
		// no adjustors for other parameters
	}
	
	/**
	 * The method to set up the grid layout in the scene.
	 * 
	 */
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);		
		// can add other grid layouts
	}
	
	/**
	 * The method to update the parameters displayed at the top of the UI Screen.
	 */
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
	}

}
