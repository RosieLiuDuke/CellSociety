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
public class XMLInputParser extends DefaultHandler{
	
	XMLParametersController inputController;
	
	private boolean bNCol = false;
	private boolean bNRow = false;
	private boolean bTotal = false;
	private boolean bDefault = false;
	private boolean bCol = false;
	private boolean bRow = false;
	private boolean bPercentage = false;
	private boolean bColor = false;
	private boolean bSpeed = false;
	private boolean bProb = false;
	private boolean bSatisfaction = false;
	private boolean bTurnover = false;
	private double turnover = 0;
	private int state = 0;
	private int row = 0;
	private int col = 0;
	private double percentage;
	private String color;
	
	/**
	 * The constructor of the XMLParser class.
	 * All the class requires is the current WelcomePage.
	 * @param p
	 */
	public XMLInputParser(Parameters p){
		inputController = new XMLParametersController(p);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		try{
			if (qName.equals("Simulation")) {
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
			else if (qName.equals("color")){
				bColor = true;
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
			DisplayAlert.displayAlert(e.getMessage());
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("nCol")) {
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
			inputController.setStateColor(state, color);
			row = col = 0;
			state = 0;
			percentage = 0;
			turnover = 0;
			color = "";
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
		else if (qName.equals("color")){
			bColor = false;
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
			else if (bColor){
				color = new String(ch, start, length);
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
			DisplayAlert.displayAlert(e.getMessage());
		}
	}	
}
