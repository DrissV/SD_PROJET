import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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

	public Deque<Route> bfs(Airport airportSource, Airport airportDestination) {
		Queue<Airport> queue = new ArrayDeque<Airport>();
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
	}

	public void calculerItineraireMinimisantDistance(String aeroport1, String aeroport2, String nomFichier) {
		bfs(airports.get(aeroport1), airports.get(aeroport2));
		System.out.println(Util.distance(airports.get(aeroport1).getLattitude(), airports.get(aeroport1).getLongitude(),
				airports.get(aeroport2).getLattitude(), airports.get(aeroport2).getLongitude()));
	}

}
