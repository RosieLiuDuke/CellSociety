import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler{
	
	boolean bGameOfLife = false;
	boolean bGrid = false;
	boolean bNRow = false;
	boolean bNCol = false;
	boolean bSize = false;
	boolean bSpeed = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
		if (qName.equals("GameOfLife")) {
			bGameOfLife = true;
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

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (bGameOfLife) {
			
		} else if (bGrid) {
			
		} else if (bNRow) {
			
		} else if (bNCol) {
			
		} else if (bSize) {
			
		} else if (bSpeed) {
			
		}
	}	
	
	public static void readFile (File inputFile){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XMLParser userhandler = new XMLParser();
			saxParser.parse(inputFile, userhandler);     
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

}
