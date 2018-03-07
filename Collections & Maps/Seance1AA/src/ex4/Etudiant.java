package ex4;

public class Etudiant implements Comparable<Etudiant> {
	
	private final String nom;
	private int moyenne;

	public Etudiant(String nom, int moyenne) {
		super();
		this.nom = nom;
		this.moyenne = moyenne;
	}

	public String getNom() {
		return nom;
	}

	public int getMoyenne() {
		return moyenne;
	}

	@Override
	public int compareTo(Etudiant et) {
		return et.moyenne - moyenne;
	}

}
