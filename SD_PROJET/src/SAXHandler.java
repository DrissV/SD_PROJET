import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAXHandler extends DefaultHandler {

	private Graph graph;
	private Airport airport;
	private List<Airport> airports;
	private double longitude, latitude;
	private boolean okLatitude, okLongitude, okAirline;
	private Airline airline;
	private List<Airline> airlines;
	private Route route;
	private List<Route> routes;
	private Map<String, Airport> iataAirport;
	private Map<String, Airline> iataAirlines;

	public SAXHandler() {
		airlines = new ArrayList<Airline>();
		airports = new ArrayList<Airport>();
		routes = new ArrayList<Route>();
		iataAirport = new HashMap<String, Airport>();
		iataAirlines = new HashMap<String, Airline>();
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
			route = new Route(iataAirlines.get(attributes.getValue("airline")),
					iataAirport.get(attributes.getValue("source")),
					iataAirport.get(attributes.getValue("destination")));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("airport".equalsIgnoreCase(qName)) {
			airport.setLattitude(latitude);
			airport.setLongitude(longitude);
			airports.add(airport);
			iataAirport.put(airport.getIata(), airport);
		}
		if ("airline".equalsIgnoreCase(qName)) {
			airlines.add(airline);
			iataAirlines.put(airline.getIata(), airline);
		}
		if ("route".equalsIgnoreCase(qName)) {
			routes.add(route);
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
		if (okAirline) {
			airline.setName(new String(ch, start, length));
			okAirline = false;
		}
	}
}
