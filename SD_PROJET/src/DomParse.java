import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class DomParse {

  public static void main(String[] args) {
    try {
      File xmlFile = new File("flight.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);
      Graph g = new Graph();
      NodeList airports = doc.getElementsByTagName("airport");
      for (int i = 0; i < airports.getLength(); i++) {
        Node nAirport = airports.item(i);
        Element eAirport = (Element) nAirport;
        Airport airport = new Airport(eAirport.getAttribute("iata"), eAirport.getAttribute("name"),
            eAirport.getAttribute("city"), eAirport.getAttribute("country"));
        airport.setLatitude(
            Double.valueOf(eAirport.getElementsByTagName("latitude").item(0).getTextContent()));
        airport.setLongitude(
            Double.valueOf(eAirport.getElementsByTagName("longitude").item(0).getTextContent()));
        g.addAirport(airport);
      }
      NodeList airlines = doc.getElementsByTagName("airline");
      for (int i = 0; i < airlines.getLength(); i++) {
        Node nAirline = airlines.item(i);
        Element eAirline = (Element) nAirline;
        Airline airline =
            new Airline(eAirline.getAttribute("iata"), eAirline.getAttribute("country"));
        g.addAirline(airline);
      }
      NodeList routes = doc.getElementsByTagName("route");
      for (int i = 0; i < routes.getLength(); i++) {
        Node nRoute = routes.item(i);
        Element eRoute = (Element) nRoute;
        g.addRoute(eRoute.getAttribute("airline"), eRoute.getAttribute("source"),
            eRoute.getAttribute("destination"));
      }
      g.calculerItineraireMinimisantDistance("BRU", "PPT", "output.xml");
      g.calculerItineraireMinimisantNombreVol("BRU", "PPT", "output2.xml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
