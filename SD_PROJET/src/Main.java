import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {
	public static void main(String[] args) {
		try {
			File inputFile = new File("flight.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler userhandler = new SAXHandler();
			saxParser.parse(inputFile, userhandler);
			Graph g = userhandler.getGraph();
			g.calculerItineraireMinimisantDistance("BRU", "PPT", "output.xml");
			g.calculerItineraireMinimisantNombreVol("BRU", "PPT", "output2.xml");
			System.out.println(g.nbrComposantesConnexes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
