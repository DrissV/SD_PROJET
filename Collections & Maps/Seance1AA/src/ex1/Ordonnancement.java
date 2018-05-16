package ex1;

import java.util.ArrayDeque;
import java.util.Queue;

public class Ordonnancement {
	public static final int NIVEAU_PRIORITE_MAX = 5;

	private Queue<Tache>[] taches;

	public Ordonnancement() {
		this.taches = new ArrayDeque[NIVEAU_PRIORITE_MAX];
		for (int i = 0; i < taches.length; i++) {
			taches[i] = new ArrayDeque<Tache>();
		}
	}

	public void ajouterTache(String descriptif, int priorite) {
		if (priorite < 1 || priorite > NIVEAU_PRIORITE_MAX || descriptif.isEmpty() || descriptif == null) {
			throw new IllegalArgumentException();
		}
		taches[priorite - 1].add(new Tache(descriptif, priorite));
	}

	// renvoie la tache prioritaire
	// renvoie null si plus de tache presente
	public Tache attribuerTache() {
		for (int i = taches.length - 1; i >= 0; i--) {
			if (!taches[i].isEmpty()) {
				return taches[i].poll();
			}
		}
		return null;
	}
}
