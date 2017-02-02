

public abstract class Animation {
	
	private CellSociety cellSociety;
	
	public Animation(CellSociety c) {
		cellSociety = c;
	}
	
	public abstract void calculateMove(Cell [][] cellArray);
}
