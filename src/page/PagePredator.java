package page;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cellSociety.CellSociety;
import javafx.scene.paint.Color;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * 
 */

public class PagePredator extends withProbability {
	private Map<Map<Integer,String>, Double> seaItems;
	private Map<Integer, Double> percentage;
	
	@Override
	public String getItemName(int state){
		for (Map.Entry<Map<Integer, String>, Double> entry: seaItems.entrySet()){
			if (entry.getKey().containsKey(state)){
				return entry.getKey().get(state);
			}
		}
		return null;
	}
	@Override
	public int getItemState(String name){
		for (Map.Entry<Map<Integer, String>, Double> entry: seaItems.entrySet()){
			for (Map.Entry<Integer, String> newEntry: entry.getKey().entrySet()){
				if (newEntry.getValue().equals(name)){
					return newEntry.getKey();
				}
			}
		}
		return 0;
	}
	@Override
	public double getItemTurnover(int state){
		for (Map.Entry<Map<Integer, String>, Double> entry: seaItems.entrySet()){
			for (Map.Entry<Integer, String> newEntry: entry.getKey().entrySet()){
				if (newEntry.getKey() == state){
					return entry.getValue();
				}
			}
		}
		return 0;
	}
	@Override
	public double getItemTurnOver(String name){
		for (Map.Entry<Map<Integer, String>, Double> entry: seaItems.entrySet()){
			for (Map.Entry<Integer, String> newEntry: entry.getKey().entrySet()){
				if (newEntry.getValue().equals(name)){
					return entry.getValue();
				}
			}
		}
		return 0;
	}
	@Override
	public void inputSeaItem(int state, String name, double turnover){
		Map<Integer, String> stateName = new HashMap<>();
		stateName.put(state, name);
		seaItems.put(stateName, turnover);
	}
	
	public PagePredator(CellSociety cs, String language) {
		super(cs, language);
		this.getColorMap().clear();
		this.getColorMap().put(0, Color.BLUE);
		this.getColorMap().put(1, Color.CORAL);
		this.getColorMap().put(2, Color.CHARTREUSE);
		seaItems = new HashMap<Map<Integer,String>, Double>();
		percentage = new HashMap<Integer, Double>();
	}
	
	@Override
	protected void setupComponents(){
		super.setupComponents();
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
	}
	
	@Override
	public double getPercentage(int state){
		return percentage.get(state);
	}
	
	@Override
	public void setPercentage(int type, double value){
		percentage.put(type, value);
	}
	
	@Override
	public void updateSliders(){
		addProbability(percentage);
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
				break;
			}
			prevStateProb += percentage.get(i);
		}
		return status;
	}
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText();
		for (Map.Entry<Integer, Double> entry : percentage.entrySet()){
		    myText += getMyResources().getString("PercentageParameter") + entry.getKey() + ": " + entry.getValue() + "\n";
		}
		this.getParameters().setText(myText);

	}	
}