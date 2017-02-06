package page;

import java.util.HashMap;
import java.util.Map;

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
	public void setSatisfaction(double value){
		satisfaction = value;
	}
	
	@Override
	public void setPercentage(int state, double value){
		percentage.put(state, value);
	}
	
	public PageSegregation(CellSociety cs) {
		super(cs);
		this.getColorMap().put(0, Color.TRANSPARENT);
		this.getColorMap().put(1, Color.RED);
		this.getColorMap().put(2, Color.BLUE);
		percentage = new HashMap<Integer, Double>();
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
	public void updateTextInfo() {
		String text = "Simulation name: " + this.getCellSociety().getCurrentType() 
				+ "\nNumber of rows: " + getRow() + " | " 
				+ "Number of columns: " + getCol() + " | "  
				+ "Cell size: " + getSize() + " | "
				+ "Step speed: " + getSpeed() + " | " 
				+ "Satisfactory Level: " + getSatisfaction() + " | " 
				+ "\n";
		for (Map.Entry<Integer, Double> entry : percentage.entrySet()){
		    text += "Percentage of type " + entry.getKey() + ": " + entry.getValue() + " | ";
		}
		text += "\nStep: " + getCurrentStep() + " | ";
		this.getParameters().setText(text);
	}	
}