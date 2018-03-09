
public class Airport {

	private double longitude, lattitude;
	private String iata, name, city, country;
	
	public Airport(String iata, String name, String city, String country) {
		super();
		if (!iata.matches("([0-9]|[A-Z]){3}")) {
			throw new IllegalArgumentException("IataAirport pattern incorrect");
		}
		this.iata = iata;
		this.name = name;
		this.city = city;
		this.country = country;
	}

	public String getIata() {
		return iata;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public double getLattitude() {
		return lattitude;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	@Override
	public int hashCode() {
		return iata.hashCode() + 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Airport other = (Airport) obj;
		return iata.equals(other.iata);
	}
}
