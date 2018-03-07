package ex6;

import java.util.TreeMap;

public class GestionImpression {

	private TreeMap<String, Impression> ordreImpressions;

	public GestionImpression() {
		this.ordreImpressions = new TreeMap<String, Impression>();
	}

	public void ajouterImpression(Impression impr) {
		ordreImpressions.put(impr.getIdUtilisateur(), impr);
	}

	public Impression selectionnerImpression() {
		if (ordreImpressions.isEmpty()) {
			return null;
		}
		return ordreImpressions.pollFirstEntry().getValue();
	}

}
