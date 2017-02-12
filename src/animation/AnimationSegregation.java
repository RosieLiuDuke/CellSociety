package animation;

import java.util.ArrayList;

import cellSociety.CellSociety;
import page.Page;
import page.PageGameOfLife;
import page.PageSegregation;
import page.Parameters;

public class AnimationSegregation extends Animation {

	private final static int NOVALUE = 0;
	private final static int FIRSTVALUE = 1;
	private final static int SECONDVALUE = 2;

	private double neededNeighbors;

	public AnimationSegregation(CellSociety c, Parameters p) {
		super(c, p);
	}

	public void calculateMove() {
		neededNeighbors = this.getParametersController().getSatisfaction();

		int[][] grid = getArray("Segregation");
		boolean[][] shouldChange = new boolean[grid.length][grid[0].length];

		checkSurrounding(shouldChange, grid);

		changeCells(shouldChange, grid);

		setCells(grid, (PageSegregation) getNeededPage("Segregation"));
	}

	private void checkSurrounding(boolean[][] shouldChange, int[][] grid) {
		int i, j, k;
		double agree = 0, disagree = 0;
		ArrayList<Coord> neighbors;
		Grid g = new SquareGrid();

		for (i = 1; i < grid.length; i++) {
			for (j = 1; j < grid[0].length; j++) {
				neighbors = g.getAllNeighbors(i, j, grid.length, grid[0].length);
				
				for (k = 0; k < neighbors.size(); k++) {
					if (grid[i][j] == grid[neighbors.get(k).getX()][neighbors.get(k).getY()]) {
						agree += 1;
					} else if (grid[neighbors.get(k).getX()][neighbors.get(k).getY()] != NOVALUE) {
						disagree += 1;
					}

				}
				shouldChange[i][j] = ((agree / disagree) <= neededNeighbors);
				agree = 0;
				disagree = 0;
			}
		}
	}

	private void changeCells(boolean[][] shouldChange, int[][] grid) {
		int i, j, a, b, rand;
		ArrayList<Coord> emptyCells = new ArrayList<Coord>();
		ArrayList<Coord> changeCells = new ArrayList<Coord>();
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == NOVALUE)
					emptyCells.add(new Coord(i, j));
				if (shouldChange[i][j])
					changeCells.add(new Coord(i, j));
			}
		}

		while (changeCells.size() > 0) {
			i = changeCells.get(0).getX();
			j = changeCells.get(0).getY();

			rand = (int) Math.random() * (emptyCells.size() - 1);
			a = emptyCells.get(rand).getX();
			b = emptyCells.get(rand).getY();

			grid[a][b] = grid[i][j];
			grid[i][j] = NOVALUE;
			emptyCells.remove(rand);
			emptyCells.add(new Coord(i, j));
			changeCells.remove(0);
		}
	}
}
