package page;

import cellSociety.CellSociety;
import javafx.scene.paint.Color;

public class PageSlime extends GamePage {

	public PageSlime(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
		this.getParametersController().addColor(0, Color.BLACK);
		this.getParametersController().addColor(1, Color.GREY);
		this.getParametersController().addColor(2, Color.WHITE);
		this.getParametersController().addColor(3, Color.RED);
	}

	@Override
	public void updateTextInfo() {
		// TODO Auto-generated method stub
		
	}

}
