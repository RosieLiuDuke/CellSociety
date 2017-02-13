package util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import page.Parameters;

public class XMLConfigParser extends DefaultHandler {
	
	XMLParametersController inputController;
	
	private boolean bShape = false;
	private boolean bVisible = false;

	public XMLConfigParser(Parameters p) {
		inputController = new XMLParametersController(p);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		try{
			if (qName.equals("shape")) {
				bShape = true;
			}
			else if (qName.equals("visible")){
				bVisible = true;
			}
		}
		catch(Exception e){
			DisplayAlert.displayAlert(e.getMessage());
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("shape")) {
			bShape = false;
		}
		else if (qName.equals("visible")){
			bVisible = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		try{
			if (bShape) {
				inputController.setShape(new String(ch, start, length));
			} 
			else if (bVisible) {
				inputController.setGridVisible(Boolean.parseBoolean(new String(ch, start, length)));
			}
		}
		catch(Exception e){
			DisplayAlert.displayAlert(e.getMessage());
		}
	}

}
