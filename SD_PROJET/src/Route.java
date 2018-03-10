
public class Route {

	private Airline airline;
	private Airport source, destination;

	public Route(Airline airline, Airport source, Airport destination) {
		super();
		this.airline = airline;
		this.source = source;
		this.destination = destination;
	}

	public Airline getAirline() {
		return airline;
	}

	public Airport getSource() {
		return source;
	}

	public Airport getDestination() {
		return destination;
	}

	public double calculerDistance() {
		return Util.distance(source.getLattitude(), source.getLongitude(), destination.getLattitude(),
				destination.getLattitude());
	}

	@Override
	public String toString() {
		return "Route [airline=" + airline + ", source=" + source + ", destination=" + destination
				+ ", calculerDistance()=" + calculerDistance() + "]";
	}

}
