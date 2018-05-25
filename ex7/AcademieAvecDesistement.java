package ex7;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AcademieAvecDesistement {

	private Map<String, Queue<String>> instruments;
	private Set<String> desistements;

	public AcademieAvecDesistement(ArrayList<String> v) {
		this.instruments = new HashMap<String, Queue<String>>();
		this.desistements = new HashSet<String>();
		for (String s : v) {
			this.instruments.put(s, new ArrayDeque<String>());
		}
	}

	public void mettreEnAttente(String instrument, String nomEleve) {
		instruments.get(instrument).add(nomEleve);
		if (desistement(nomEleve)) {
			desistements.remove(nomEleve);
		}
	}

	public void desistement(String instrument, String eleve) {
		desistements.add(eleve);
	}

	public boolean desistement(String eleve) {
		return desistements.contains(eleve);
	}

	// supprime uniquement l'élève de la file d'attente
	public String attribuerPlace(String instrument) {
		while(!instruments.get(instrument).isEmpty()){
			String eleve = instruments.get(instrument).poll();
			if(!desistement(eleve)){
				return eleve;
			}
		}
		return null;
	}

}
