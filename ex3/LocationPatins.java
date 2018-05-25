package ex3;

import static java.time.temporal.ChronoUnit.MILLIS;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LocationPatins {

	private List<Integer> casiers;
	private Map<Integer, LocalTime> casiersOccupes;
	// pointure, liste des casiers libres pour cette pointure
	private Map<Integer, LinkedList<Integer>> casiersLibres;

	public LocationPatins(int[] casiers) {
		this.casiers = new ArrayList<Integer>();
		this.casiersOccupes = new HashMap<Integer, LocalTime>();
		this.casiersLibres = new HashMap<Integer, LinkedList<Integer>>();
		for (int i = 0; i < casiers.length; i++) {
			int pointure = casiers[i];
			this.casiers.add(pointure);
			if (!casiersLibres.containsKey(pointure)) {
				casiersLibres.put(pointure, new LinkedList<Integer>());
			}
			casiersLibres.get(pointure).add(i);
		}
	}

	// date1 < date2
	private static double prix(LocalTime date1, LocalTime date2) {
		// 1 euro par milliseconde (c'est assez cher en effet)
		return MILLIS.between(date1, date2);
	}

	public int attribuerCasierAvecPatins(int pointure) {
		if (pointure < 33 || pointure > 48)
			throw new IllegalArgumentException();
		LocalTime l = LocalTime.now();
		if (casiersLibres.get(pointure).isEmpty()) {
			return -1;
		}
		int casier = casiersLibres.get(pointure).remove(0);
		casiersOccupes.put(casier, l);
		return casier;
	}

	public double libererCasier(int numeroCasier) {
		if (numeroCasier < 0 || numeroCasier > casiers.size()) {
			throw new IllegalArgumentException();
		}
		casiersLibres.get(casiers.get(numeroCasier)).add(numeroCasier);
		return prix(casiersOccupes.remove(numeroCasier), LocalTime.now());
	}

}
