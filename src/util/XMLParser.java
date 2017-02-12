package util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import page.Parameters;

/**
 * The handler to parse XML file with Java SAX package.
 * @author Yilin Gao
 *
 */
public class XMLParser extends DefaultHandler{
	
	Parameters parametersController;
	
	boolean bSimulation = false;
	boolean bName = false;
	boolean bGrid = false;
	boolean bNCol = false;
	boolean bNRow = false;
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
	
	/**
	 * The constructor of the XMLParser class.
	 * All the class requires is the current WelcomePage.
	 * @param p
	 */
	public XMLParser(Parameters p){
		parametersController = p;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("Simulation")) {
			bSimulation = true;
			type = attributes.getValue("name");
			parametersController.setType(type);
		} 
		else if (qName.equals("grid")) {
			bGrid = true;
		}  
		else if (qName.equals("nCol")) {
			bNCol = true;
		} 
		else if (qName.equals("nRow")) {
			bNRow = true;
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

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("Simulation")) {
			bSimulation = false;
		} 
		else if (qName.equals("grid")) {
			bGrid = false;
		}  
		else if (qName.equals("nCol")) {
			bNCol = false;
		} 
		else if (qName.equals("nRow")) {
			bNRow = false;
		}
		else if (qName.equals("default")){
			bDefault = false;
		}
		else if (qName.equals("state")){
			parametersController.setCellStatus(col, row, state);
			parametersController.setStatusPercentage(state, percentage);
			parametersController.addSeaItem(state, turnover);
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
		if (bNCol) {
			parametersController.setColNum(Integer.parseInt(new String(ch, start, length)));
		} 
		else if (bNRow) {
			parametersController.setRowNum(Integer.parseInt(new String(ch, start, length)));
		}
		else if (bDefault){
			parametersController.setDefaultStatus(Integer.parseInt(new String(ch, start, length)));
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
			parametersController.setSpeed(Double.parseDouble(new String(ch, start, length)));
		}
		else if (bProb){
			parametersController.setProb(Double.parseDouble(new String(ch, start, length)));
		}
		else if (bSatisfaction){
			parametersController.setSatisfaction(Double.parseDouble(new String(ch, start, length)));
		}
		else if (bTurnover){
			turnover = Double.parseDouble(new String(ch, start, length));
		}
	}	

}
