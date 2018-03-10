import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
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
		System.out
				.println(itineraires.stream().collect(Collectors.summingDouble(Route::calculerDistance)).doubleValue());
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
			int index = 0;
			double distance = 0;
			for (Route route : itineraires) {
				index++;
				System.out.println(index);
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
				Element source = doc.createElement("source");
				/*Attr iataCode = doc.createAttribute("iataCode");
				iataCode.setValue(airportSource.getIata());
				source.setAttributeNode(iataCode);
				Attr pays = doc.createAttribute("pays");
				pays.setValue(airportSource.getCountry());
				source.setAttributeNode(pays);
				Attr ville = doc.createAttribute("ville");
				ville.setValue(airportSource.getCity());
				source.setAttributeNode(ville);
				source.appendChild(doc.createTextNode(airportSource.getCity()));*/
				source = vol(doc, source, airportSource);
				vol.appendChild(source);
				Element destination = doc.createElement("destination");
				/*iataCode = doc.createAttribute("iataCode");
				iataCode.setValue(airportDestination.getIata());
				destination.setAttributeNode(iataCode);
				pays = doc.createAttribute("pays");
				pays.setValue(airportDestination.getCountry());
				destination.setAttributeNode(pays);
				ville = doc.createAttribute("ville");
				ville.setValue(airportDestination.getCity());
				destination.setAttributeNode(ville);
				destination.appendChild(doc.createTextNode(airportDestination.getCity()));*/
				destination = vol(doc, destination, airportDestination);
				vol.appendChild(destination);
				rootElement.appendChild(vol);
				if (index == itineraires.size()) {
					Attr arrivee = doc.createAttribute("destination");
					arrivee.setValue(airportDestination.getName());
					rootElement.setAttributeNode(arrivee);
					Attr distanceTotale = doc.createAttribute("distance");
					distanceTotale.setValue(String.valueOf(distance));
					rootElement.setAttributeNode(distanceTotale);
				}
			}
			doc.appendChild(rootElement);
			// enregistrer dans un fichier
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			/*DOMImplementation domImpl = doc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype", "SYSTEM", "trajet.dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());*/
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(nomFichier));
			transformer.transform(source, result);
			System.out.println("Ok enregistrement fichier");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Element vol(Document doc, Element el, Airport airport) {
		Attr iataCode = doc.createAttribute("iataCode");
		iataCode.setValue(airport.getIata());
		el.setAttributeNode(iataCode);
		Attr pays = doc.createAttribute("pays");
		pays.setValue(airport.getCountry());
		el.setAttributeNode(pays);
		Attr ville = doc.createAttribute("ville");
		ville.setValue(airport.getCity());
		el.setAttributeNode(ville);
		el.appendChild(doc.createTextNode(airport.getCity()));
		return el;
	}

}
