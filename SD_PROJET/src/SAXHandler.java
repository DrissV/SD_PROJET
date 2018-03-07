import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

	private Graph graph;
	private Airport airport;
	private List<Airport> airports;
	private double longitude, latitude;
	private boolean okLatitude, okLongitude;
	
	public SAXHandler() {
		airports = new ArrayList<Airport>();
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("airport".equalsIgnoreCase(qName)) { 
			airport = new Airport (attributes.getValue("iata"), attributes.getValue("name"), attributes.getValue("city"), attributes.getValue("country"));
		}
		if ("longitude".equalsIgnoreCase(qName)) {
			okLongitude = true;
		}
		if ("latitude".equalsIgnoreCase(qName)) {
			okLatitude = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("airport".equalsIgnoreCase(qName)) {
			airport.setLattitude(latitude);
			airport.setLongitude(longitude);
			airports.add(airport);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (okLongitude) {
			longitude = Double.parseDouble(new String(ch, start, length));
			okLongitude = false;
		}
		if (okLatitude) {
			latitude = Double.parseDouble(new String(ch, start, length));
			okLatitude = false;
		}
	}

}
