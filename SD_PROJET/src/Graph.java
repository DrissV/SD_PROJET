import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Graph {

	private Map<String, Airport> airports;
	private Map<String, Airline> airlines;
	private Map<Airport, Set<Route>> vols;

	public Graph() {
		airports = new HashMap<String, Airport>();
		airlines = new HashMap<String, Airline>();
		vols = new HashMap<Airport, Set<Route>>();
	}

	public void addRoute(String airline, String source, String destination) {
		Airport airportSource = airports.get(source);
		Route route = new Route(airlines.get(airline), airportSource, airports.get(destination));
		if (!vols.containsKey(airportSource)) {
			vols.put(airportSource, new HashSet<Route>());
		}
		vols.get(airportSource).add(route);
	}

	public void addAirport(Airport airport) {
		airports.putIfAbsent(airport.getIata(), airport);
	}

	public void addAirline(Airline airline) {
		airlines.putIfAbsent(airline.getIata(), airline);
	}

	private Deque<Route> bfs(Airport airportSource, Airport airportDestination) {
		Deque<Airport> queue = new ArrayDeque<Airport>();
		Set<Airport> visites = new HashSet<Airport>();
		Map<Airport, Route> itineraires = new HashMap<Airport, Route>();
		queue.add(airportSource);
		visites.add(airportSource);
		Airport current = airportSource;
		while (!current.equals(airportDestination) && !queue.isEmpty()) {
			current = queue.remove();
			Set<Route> routes = vols.get(current);
			if (routes != null) {
				for (Route route : routes) {
					Airport destination = route.getDestination();
					if (!visites.contains(destination)) {
						queue.add(destination);
						visites.add(destination);
						itineraires.putIfAbsent(destination, route);
					}
				}
			}
		}
		Airport depart = airportDestination;
		Deque<Route> routes = new ArrayDeque<Route>();
		Route route;
		while ((route = itineraires.get(depart)) != null) {
			routes.addFirst(route);
			depart = route.getSource();
		}
		return routes;
	}

	public void calculerItineraireMinimisantNombreVol(String aeroport1, String aeroport2, String nomFichier) {
		Airport airportSource = airports.get(aeroport1), airportDestination = airports.get(aeroport2);
		Deque<Route> itineraires = bfs(airportSource, airportDestination);
		creerDocument(itineraires, nomFichier);
	}

	public void calculerItineraireMinimisantDistance(String aeroport1, String aeroport2, String nomFichier) {
		Airport airportSource = airports.get(aeroport1), airportDestination = airports.get(aeroport2);
		Deque<Route> itineraires = dijkstra(airportSource, airportDestination);
		creerDocument(itineraires, nomFichier);
	}

	private Deque<Route> dijkstra(Airport airportSource, Airport airportDestination) {
		return new ArrayDeque<Route>();
	}

	private void creerDocument(Deque<Route> itineraires, String nomFichier) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("trajet");
			int distance = 0, index = 0;
			for (Route route : itineraires) {
				index++;
				Airport airportSource = route.getSource(), airportDestination = route.getDestination();
				if (index == 1) {
					Attr depart = doc.createAttribute("depart");
					depart.setValue(airportSource.getName());
					rootElement.setAttributeNode(depart);
				}
				Element vol = doc.createElement("vol");
				Attr compagnie = doc.createAttribute("compagnie");
				compagnie.setValue(route.getAirline().getName());
				vol.setAttributeNode(compagnie);
				Attr nombreKm = doc.createAttribute("nombreKm");
				double nombreKmRoute = route.calculerDistance();
				distance += nombreKmRoute;
				nombreKm.setValue(String.valueOf(nombreKmRoute));
				vol.setAttributeNode(nombreKm);
				Attr numero = doc.createAttribute("numero");
				numero.setValue(String.valueOf(index));
				vol.setAttributeNode(numero);
				Element source = vol(doc, "source", airportSource);
				vol.appendChild(source);
				Element destination = vol(doc, "destination", airportDestination);
				vol.appendChild(destination);
				if (index == itineraires.size()) {
					Attr arrivee = doc.createAttribute("destination");
					arrivee.setValue(airportDestination.getName());
					rootElement.setAttributeNode(arrivee);
					Attr distanceTotale = doc.createAttribute("distance");
					distanceTotale.setValue(String.valueOf(distance));
					rootElement.setAttributeNode(distanceTotale);
				}
			}
			// enregistrer dans un fichier
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(nomFichier));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Element vol(Document doc, String nom, Airport airport) {
		Element el = doc.createElement(nom);
		Attr iataCode = doc.createAttribute("iataCode");
		iataCode.setValue(airport.getIata());
		el.setAttributeNode(iataCode);
		Attr pays = doc.createAttribute("pays");
		pays.setValue(airport.getCountry());
		el.setAttributeNode(pays);
		Attr ville = doc.createAttribute("ville");
		ville.setValue(airport.getCity());
		el.setAttributeNode(ville);
		return el;
	}

}
