package animation;

import java.util.ArrayList;
import java.util.List;

import cell.Cell;
import cellSociety.CellSociety;
import page.PagePredator;

public class AnimationPredator extends Animation{
	
	private final static int EMPTYNUMBER = 0;
	private final static int FISHNUMBER = 1;
	private final static int SHARKNUMBER = 2;
	
	private int sharkLife;
	private int fishLife;
	private boolean firstTime;
	private int xMax;
	private int yMax;
	
	private ArrayList<Fish> fishList;
	private ArrayList<Shark> sharkList;
	private ArrayList<cellObject> emptyList;
	
	private class cellObject {
		
		private int x;
		private int y;
		
		private cellObject (int i, int j) {
			x=i;
			y=j;
		}
		protected int getX() {
			return x;
		}
		
		protected int getY() {
			return y;
		}
		protected void setX(int i) {
			x = i;
		}
		protected void setY(int i) {
			y = i;
		}
	}
	
	private abstract class Creature extends cellObject {
		private int life;
		
		private Creature (int i, int j) {
			super(i, j);
		}
		
		protected int getLife() {
			return life;
		}
	}
	
	private class Fish extends Creature {
		
		private Fish (int i, int j) {
			super(i, j);
			super.life = 0;
		}
		
		private void addLife() {
			super.life++;
		}
		private void resetLife() {
			super.life = 0;
		}
	}
	
	private class Shark extends Creature {
		private int x;
		private int y;
		
		private Shark (int i, int j) {
			super(i, j);
			super.life = sharkLife;
		}
		
		private void subLife() {
			super.life--;
		}
		private void resetLife() {
			super.life = sharkLife;
		}
	}

	public AnimationPredator(CellSociety c) {
		super(c);
		firstTime = true;
		fishList = new ArrayList<Fish>();
		sharkList = new ArrayList<Shark>();
		emptyList = new ArrayList<cellObject>();
	}
	
	public void calculateMove() {
		int [][] grid = getArray("Predator");
		
		xMax = grid.length;
		yMax = grid[0].length;
		
		if (firstTime)
			createLists(grid);
		
		progressThroughLists();
		updateGrid(grid);
		setCells(grid, (PagePredator)getNeededPage("Predator"));
	}
	
	private void createLists(int [][] grid) {
		int i,j;
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == FISHNUMBER) {
					fishList.add(new Fish(i, j));
				}
				else if (grid[i][j] == SHARKNUMBER) {
					sharkList.add(new Shark(i,j));
				}
				else {
					emptyList.add(new cellObject(i, j));
				}
			}
		}
	}
	
	private void progressThroughLists() {
		
		sharkProgress();
		fishProgress();
	}
	
	private void sharkProgress () {
		int i, rand, x, y;
		int initsize = sharkList.size();
		
		for (i = 0; i < initsize; i++) {
			
			if (sharkList.get(i).getLife() == 0) {
				emptyList.add(new cellObject(sharkList.get(i).getX(), sharkList.get(i).getY()));
				sharkList.remove(i);
				i--;
				initsize--;
			}
			else if (checkFish(sharkList.get(i)).size() > 0) {
				sharkList.get(i).resetLife();
				rand = getRandomForList(checkFish(sharkList.get(i)).size());
				x = checkFish(sharkList.get(i)).get(rand).getX();
				y = checkFish(sharkList.get(i)).get(rand).getY();
				fishList.remove(new Fish(x, y));
				sharkList.add(new Shark(x, y));
			}
			else if (checkEmpty(sharkList.get(i)).size() > 0) {
				sharkList.get(i).subLife();
				rand = getRandomForList(checkEmpty(sharkList.get(i)).size());
				x = checkEmpty(sharkList.get(i)).get(rand).getX();
				y = checkEmpty(sharkList.get(i)).get(rand).getY();
				emptyList.remove(new cellObject(x, y));
				emptyList.add(new cellObject(sharkList.get(i).getX(), sharkList.get(i).getY()));
				sharkList.get(i).setX(x);
				sharkList.get(i).setY(y);
			}
			else {
				sharkList.get(i).subLife();
			}
		}
	}
	private void fishProgress() {
		int i, rand, x, y;
		int initsize = fishList.size();
		
		for (i = 0; i < initsize; i++) {
			if (checkEmpty(fishList.get(i)).size() != 0) {
				System.out.println(checkEmpty(fishList.get(i)));
				if (fishList.get(i).getLife() >= fishLife) {
					fishList.get(i).resetLife();
					rand = getRandomForList(checkEmpty(sharkList.get(i)).size());
					x = checkEmpty(sharkList.get(i)).get(rand).getX();
					y = checkEmpty(sharkList.get(i)).get(rand).getY();
					emptyList.remove(new cellObject(x, y));
					fishList.add(new Fish(x,y));
				}
				else {
					fishList.get(i).addLife();
					rand = getRandomForList(checkEmpty(sharkList.get(i)).size());
					x = checkEmpty(sharkList.get(i)).get(rand).getX();
					y = checkEmpty(sharkList.get(i)).get(rand).getY();
					emptyList.remove(new cellObject(x, y));
					emptyList.add(new cellObject(fishList.get(i).getX(), fishList.get(i).getY()));
					fishList.get(i).setX(x);
					fishList.get(i).setY(y);
				}
			}
			else {
				System.out.println("This happens");
				fishList.get(i).addLife();
			}
		}
	}
	
	private ArrayList <Fish> checkFish(Creature c) {
		ArrayList <Fish> returnList = new ArrayList<Fish>();
		checkFishSpot(c.getX() -1, c.getY(),returnList);
		checkFishSpot(c.getX() + 1, c.getY(), returnList);
		checkFishSpot(c.getX(), c.getY() - 1, returnList);
		checkFishSpot(c.getX(), c.getY() + 1, returnList);
		return returnList;
	}
	private void checkFishSpot (int x, int y, ArrayList <Fish> returnList) {
		if ((x >= 0) && (y>= 0) && (x < xMax) && (y < yMax)) {
			if (fishList.contains(new Fish(x, y))) {
				returnList.add(new Fish(x,y));
			}
		}
	}
	private ArrayList <cellObject> checkEmpty(Creature c) {
		ArrayList <cellObject> returnList = new ArrayList<cellObject>();
		checkEmptySpot(c.getX() -1, c.getY(),returnList);
		checkEmptySpot(c.getX() + 1, c.getY(), returnList);
		checkEmptySpot(c.getX(), c.getY() - 1, returnList);
		checkEmptySpot(c.getX(), c.getY() + 1, returnList);
		return returnList;
	}
	private void checkEmptySpot(int x, int y, ArrayList <cellObject>returnList) {
		int i;
		if ((x >= 0) && (y>= 0) && (x < xMax) && (y < yMax)) {
			for (i = 0; i < emptyList.size(); i++) {
				if ((emptyList.get(i).getX() == x) && (emptyList.get(i).getY() == y)){
					returnList.add(new cellObject(x,y));
				}
			}
		}
	}
	
	private int getRandomForList (int size) {
		return (int)((Math.random() * size) - .001);
	}
	
	//Cannot figure out how to not duplicate code due to the types in the arraylist
	private void updateGrid(int [][] grid) {
		int i;
		for (i = 0; i < sharkList.size(); i++) {
			grid[sharkList.get(i).getX()][sharkList.get(i).getY()] = SHARKNUMBER;
		}
		for (i = 0; i < fishList.size(); i++) {
			grid[fishList.get(i).getX()][fishList.get(i).getY()] = FISHNUMBER;
		}
		for (i = 0; i < emptyList.size(); i++) {
			grid[emptyList.get(i).getX()][emptyList.get(i).getY()] = EMPTYNUMBER;
		}
	}
}
