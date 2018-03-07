
public class Airline {

	public String iata, country;

	public Airline(String iata, String country) {
		super();
		this.iata = iata;
		this.country = country;
	}

	public String getIata() {
		return iata;
	}

	public String getCountry() {
		return country;
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
