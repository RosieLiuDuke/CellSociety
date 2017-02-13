package page;

import cellSociety.CellSociety;

public class PageSlime extends UIsetupWithPercentage{
	
	public PageSlime(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}

	@Override
	protected void setupComponents(){
		super.setupComponents();
		// no adjustors for other parameters
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);		
		// can add other grid layouts
	}
	
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
	}

}
