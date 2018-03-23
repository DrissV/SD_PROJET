
public class Airport {

	private double longitude, latitude, cout;
	private String iata, name, city, country;

	public Airport(String iata, String name, String city, String country) {
		super();
		if (!iata.matches("([0-9]|[A-Z]){3}")) {
			throw new IllegalArgumentException("IataAirport pattern incorrect");
		}
		this.cout = Double.MAX_VALUE;
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

	public double getLatitude() {
		return latitude;
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

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iata == null) ? 0 : iata.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		if (iata == null) {
			if (other.iata != null)
				return false;
		} else if (!iata.equals(other.iata))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Airport [longitude=" + longitude + ", latitude=" + latitude + ", iata=" + iata + ", name=" + name
				+ ", city=" + city + ", country=" + country + "]";
	}
	
	public double getCout() {
		return cout;
	}
	
	public void setCout(double cout) {
		this.cout = cout;
	}

}
