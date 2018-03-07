import java.util.HashMap;
import java.util.Map;

public class Graph {

	private Map<String, Airport> map = new HashMap<>();
	
	public void calculerItineraireMinimisantNombreVol(String aeroport1, String aeroport2, String nomFichier) {
		System.out.println(Util.distance(map.get(aeroport1).getLattitude(), map.get(aeroport1).getLongitude(), map.get(aeroport2).getLattitude(), map.get(aeroport2).getLongitude()));
	}
	
	public void calculerItineraireMinimisantDistance(String aeroport1, String aeroport2, String nomFichier) {
		System.out.println(Util.distance(map.get(aeroport1).getLattitude(), map.get(aeroport1).getLongitude(), map.get(aeroport2).getLattitude(), map.get(aeroport2).getLongitude()));
	}
	
}
