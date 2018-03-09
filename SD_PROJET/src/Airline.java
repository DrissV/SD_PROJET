
public class Airline {

	private String iata, country, name;

	public Airline(String iata, String country) {
		super();
		if (!iata.matches("_([0-9]|[A-Z]){2}")) {
			throw new IllegalArgumentException("IataAirline pattern incorrect");
		}
		this.iata = iata;
		this.country = country;
	}

	public String getIata() {
		return iata;
	}

	public String getCountry() {
		return country;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
		Airline other = (Airline) obj;
		return iata.equals(other.iata);
	}

}
