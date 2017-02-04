import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler{
	
	PageGameOfLife page;
	
	boolean bGameOfLife = false;
	boolean bGrid = false;
	boolean bNRow = false;
	boolean bNCol = false;
	boolean bSize = false;
	boolean bSpeed = false;
	
	public XMLParser(PageGameOfLife p){
		page = p;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
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
		if (qName.equals("GameOfLife")) {
			bGameOfLife = false;
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
		if (bNRow) {
			page.setRowNum(Integer.parseInt(new String(ch, start, length)));
		} else if (bNCol) {
			page.setColNum(Integer.parseInt(new String(ch, start, length)));
		} else if (bSize) {
			page.setSize(Integer.parseInt(new String(ch, start, length)));
		} else if (bSpeed) {
			page.setSpeed(Integer.parseInt(new String(ch, start, length)));
		}
	}	

}
