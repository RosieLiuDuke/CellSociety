package util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import page.GamePage;
import page.WelcomePage;

public class XMLParser extends DefaultHandler{
	
	WelcomePage page;
	
	boolean bSimulation = false;
	boolean bName = false;
	boolean bGrid = false;
	boolean bNRow = false;
	boolean bNCol = false;
	boolean bSize = false;
	boolean bSpeed = false;
	String type = "";
	
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
		} else if (qName.equals("grid")) {
			bGrid = true;
		} else if (qName.equals("nRow")) {
			bNRow = true;
		} else if (qName.equals("nCol")) {
			bNCol = true;
		} else if (qName.equals("size")) {
			bSize = true;
		} else if (qName.equals("speed")){
			bSpeed = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("Simulation")) {
			bSimulation = false;
		} else if (qName.equals("grid")) {
			bGrid = false;
		} else if (qName.equals("nRow")) {
			bNRow = false;
		} else if (qName.equals("nCol")) {
			bNCol = false;
		} else if (qName.equals("size")) {
			bSize = false;
		} else if (qName.equals("speed")){
			bSpeed = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		GamePage thePage = (GamePage) page.getCellSociety().getPage(type);
		if (bNRow) {
			thePage.setRowNum(Integer.parseInt(new String(ch, start, length)));
			bNRow = false;
		} else if (bNCol) {
			thePage.setColNum(Integer.parseInt(new String(ch, start, length)));
			bNCol = false;
		} else if (bSize) {
			thePage.setSize(Integer.parseInt(new String(ch, start, length)));
			bSize = false;
		} else if (bSpeed) {
			thePage.setSpeed(Integer.parseInt(new String(ch, start, length)));
			bSpeed = false;
		}
	}	

}
