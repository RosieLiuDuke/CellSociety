package util;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cell.Indices;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import page.Parameters;
import javafx.scene.control.Label;

/**
 * The handler to parse XML file with Java SAX package.
 * @author Yilin Gao
 *
 */
public class XMLParser extends DefaultHandler{
	
	Parameters parametersController;
	XMLParametersController inputController;
	
	boolean bSimulation = false;
	boolean bName = false;
	boolean bNCol = false;
	boolean bNRow = false;
	boolean bTotal = false;
	boolean bDefault = false;
	boolean bState = false;
	boolean bCol = false;
	boolean bRow = false;
	boolean bPercentage = false;
	boolean bSpeed = false;
	boolean bProb = false;
	boolean bSatisfaction = false;
	boolean bTurnover = false;
	String type = "";
	double turnover = 0;
	int state = 0;
	int row = 0;
	int col = 0;
	double percentage;
	Map<String, Object> parameterMap = new HashMap<>();
	Map<Integer, Double> statePercentage = new HashMap<>();
	Map<Integer, Double> stateTurnover = new HashMap<>();
	Map<Indices, Integer> cellPositionState = new HashMap<>();
	
	/**
	 * The constructor of the XMLParser class.
	 * All the class requires is the current WelcomePage.
	 * @param p
	 */
	public XMLParser(Parameters p){
		parametersController = p;
		inputController = new XMLParametersController(parametersController, parameterMap);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		try{
			if (qName.equals("Simulation")) {
				bSimulation = true;
				inputController.setType(attributes.getValue("name"));
			}
			else if (qName.equals("nCol")) {
				bNCol = true;
			} 
			else if (qName.equals("nRow")) {
				bNRow = true;
			}
			else if (qName.equals("total")){
				bTotal = true;
			}
			else if (qName.equals("default")){
				bDefault = true;
			}
			else if (qName.equals("state")){
				bState = true;
				state = Integer.parseInt(attributes.getValue("value"));
			}
			else if (qName.equals("column")){
				bCol = true;
			}
			else if (qName.equals("row")){
				bRow = true;
			}
			else if (qName.equals("percentage")){
				bPercentage = true;
			}
			else if (qName.equals("speed")){
				bSpeed = true;
			}
			else if (qName.equals("prob")){
				bProb = true;
			}
			else if (qName.equals("satisfaction")){
				bSatisfaction = true;
			}
			else if (qName.equals("turnover")){
				bTurnover = true;
			}
		}
		catch(Exception e){
			displayAlert(e);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("Simulation")) {
			bSimulation = false;
		}  
		else if (qName.equals("nCol")) {
			bNCol = false;
		} 
		else if (qName.equals("nRow")) {
			bNRow = false;
		}
		else if (qName.equals("total")){
			bTotal = false;
		}
		else if (qName.equals("default")){
			bDefault = false;
		}
		else if (qName.equals("state")){
			inputController.setCellStatus(col, row, state);
			inputController.setStatusPercentage(state, percentage);
			inputController.setSeaItemTurnover(state, turnover);
			row = col = 0;
			state = 0;
			percentage = 0;
			turnover = 0;
			bState = false;
		}
		else if (qName.equals("column")){
			bCol = false;
		}
		else if (qName.equals("row")){
			bRow = false;
		}
		else if (qName.equals("percentage")){
			bPercentage = false;
		}
		else if (qName.equals("speed")){
			bSpeed = false;
		}
		else if (qName.equals("prob")){
			bProb = false;
		}
		else if (qName.equals("satisfaction")){
			bSatisfaction = false;
		}
		else if (qName.equals("turnover")){
			bTurnover = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		try{
			if (bNCol) {
				inputController.setCol(Integer.parseInt(new String(ch, start, length)));
			} 
			else if (bNRow) {
				inputController.setRow(Integer.parseInt(new String(ch, start, length)));
			}
			else if (bTotal) {
				inputController.setTotal(Integer.parseInt(new String(ch, start, length)));
			}
			else if (bDefault){
				inputController.setDefaultStatus(Integer.parseInt(new String(ch, start, length)));
			}
			else if (bCol){
				col = Integer.parseInt(new String(ch, start, length));
			}
			else if (bRow){
				row = Integer.parseInt(new String(ch, start, length));
			} 
			else if (bPercentage){
				percentage = Double.parseDouble(new String(ch, start, length));
			}
			else if (bSpeed) {
				inputController.setSpeed(Double.parseDouble(new String(ch, start, length)));
			}
			else if (bProb){
				inputController.setProbability(Double.parseDouble(new String(ch, start, length)));
			}
			else if (bSatisfaction){
				inputController.setSatisfaction(Double.parseDouble(new String(ch, start, length)));
			}
			else if (bTurnover){
				turnover = Double.parseDouble(new String(ch, start, length));
			}
		}
		catch(Exception e){
			displayAlert(e);
		}
	}	

	private void displayAlert(Exception e){
		Alert alert = new Alert(AlertType.ERROR);
		Label label = new Label(e.getMessage());
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);
		alert.showAndWait();
	}
}
