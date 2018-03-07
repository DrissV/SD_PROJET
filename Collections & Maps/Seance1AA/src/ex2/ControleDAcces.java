package ex2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ControleDAcces {
	
	private Map<Badge, Employe> badges;
	private Set<Employe> employesPresents;

	public ControleDAcces(){
		this.badges = new HashMap<Badge, Employe>();
		this.employesPresents = new HashSet<Employe>();
	}
	
	// associe le badge à un employé
	public void donnerBadge (Badge b, Employe e){
		if (estDansBatiment(e)) {
			return;
		}
		badges.put(b, e);
	}
	
	// met à jour les employés présents dans le batiment
	public void entrerBatiment (Badge b){
		if (!badges.containsKey(b)) {
			return;
		}
		employesPresents.add(badges.get(b));
	}

	// met à jour les employés présents dans le batiment
	public void sortirBatiment (Badge b){
		employesPresents.remove(badges.get(b));
	}
	
	// renvoie vrai si l'employé est dans le batiment, faux sinon
	public boolean estDansBatiment (Employe e){
		return employesPresents.contains(e);
	}

}
