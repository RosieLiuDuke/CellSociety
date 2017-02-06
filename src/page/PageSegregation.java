package page;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import cellSociety.CellSociety;
import javafx.scene.paint.Color;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * @author Harry Liu
 */

public class PageSegregation extends GamePage {
	
	private double satisfaction;
	private Map<Integer, Double> percentage;
	
	@Override
	public double getSatisfaction(){
		return satisfaction;
	}
	
	@Override
	public double getPercentage(int state){
		return percentage.get(state);
	}
	
	@Override
	protected int getCellStatus(int col, int row){
		int status = 0;
		Random rn = new Random();
		double indicator = rn.nextDouble();
		int numberOfStates = percentage.size();
		double prevStateProb = 0, nextStateProb = 0;
		for (int i = 0; i < numberOfStates; i++){
			nextStateProb += percentage.get(i);
			if (indicator >= prevStateProb && indicator < nextStateProb){
				status = i;
			}
			prevStateProb += nextStateProb;
		}
		return status;
	}
	
	@Override
	public void setSatisfaction(double value){
		satisfaction = value;
	}
	
	@Override
	public void setPercentage(int state, double value){
		percentage.put(state, value);
	}
	
	public PageSegregation(CellSociety cs) {
		super(cs);
		this.getColorMap().clear();
		this.getColorMap().put(0, Color.TRANSPARENT);
		this.getColorMap().put(1, Color.RED);
		this.getColorMap().put(2, Color.BLUE);
		percentage = new LinkedHashMap<Integer, Double>();
	}
	
	@Override
	protected void setupComponents(){
		this.getOptions().add("Input");
		super.setupComponents();
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		
	}
	
	@Override
	public void addTextInfo() {
		String myText = getText() + "Satisfactory level: " + getSatisfaction();
		for (Map.Entry<Integer, Double> entry : percentage.entrySet()){
		    myText += "Percentage of type " + entry.getKey() + ": " + entry.getValue() + " | ";
		}
		this.getParameters().setText(myText);

	}	
}