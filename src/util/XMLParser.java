package util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import page.GamePage;
import page.WelcomePage;

/**
 * The handler to parse XML file with Java SAX package.
 * @author Yilin Gao
 *
 */
public class XMLParser extends DefaultHandler{
	
	WelcomePage page;
	
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
	String type = "";
	int state = 0;
	int row = 0;
	int col = 0;
	double percentage;
	
	/**
	 * The constructor of the XMLParser class.
	 * All the class requires is the current WelcomePage.
	 * @param p
	 */
	public XMLParser(WelcomePage p){
		page = p;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("Simulation")) {
			bSimulation = true;
			type = attributes.getValue("name");
			page.getCellSociety().setNextType(type);
			page.getCellSociety().initializePage(type);
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
			GamePage thePage = (GamePage) page.getCellSociety().getPage(type);
			thePage.setCellStatus(col, row, state);
			thePage.setPercentage(state, percentage);
			row = col = 0;
			state = 0;
			percentage = 0;
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
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		GamePage thePage = (GamePage) page.getCellSociety().getPage(type); 
		if (bNCol) {
			thePage.setColNum(Integer.parseInt(new String(ch, start, length)));
		} 
		else if (bNRow) {
			thePage.setRowNum(Integer.parseInt(new String(ch, start, length)));
		}
		else if (bDefault){
			thePage.setDefaultStatus(Integer.parseInt(new String(ch, start, length)));
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
			thePage.setSpeed(Integer.parseInt(new String(ch, start, length)));
		}
		else if (bProb){
			thePage.setProb(Double.parseDouble(new String(ch, start, length)));
		}
	}	

}
