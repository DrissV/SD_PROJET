import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

	private Graph graph;
	private Airport airport;
	private double longitude, latitude;
	private boolean okLatitude, okLongitude, okAirline;
	private Airline airline;
	private Route route;

	public SAXHandler() {
		graph = new Graph();
	}

	public Graph getGraph() {
		return graph;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("airport".equalsIgnoreCase(qName)) {
			airport = new Airport(attributes.getValue("iata"), attributes.getValue("name"), attributes.getValue("city"),
					attributes.getValue("country"));
		}
		if ("longitude".equalsIgnoreCase(qName)) {
			okLongitude = true;
		}
		if ("latitude".equalsIgnoreCase(qName)) {
			okLatitude = true;
		}
		if ("airline".equalsIgnoreCase(qName)) {
			airline = new Airline(attributes.getValue("iata"), attributes.getValue("country"));
			okAirline = true;
		}
		if ("route".equalsIgnoreCase(qName)) {
			graph.addRoute(attributes.getValue("airline"), attributes.getValue("source"), attributes.getValue("destination"));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("airport".equalsIgnoreCase(qName)) {
			airport.setLattitude(latitude);
			airport.setLongitude(longitude);
			graph.addAirport(airport);
		}
		if ("airline".equalsIgnoreCase(qName)) {
			graph.addAirline(airline);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (okLongitude) {
			longitude = Double.parseDouble(new String(ch, start, length).trim());
			okLongitude = false;
		}
		if (okLatitude) {
			latitude = Double.parseDouble(new String(ch, start, length).trim());
			okLatitude = false;
		}
		if (okAirline) {
			airline.setName(new String(ch, start, length).trim());
			okAirline = false;
		}
	}
}
