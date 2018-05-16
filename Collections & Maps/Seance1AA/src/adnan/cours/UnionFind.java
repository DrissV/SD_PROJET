package adnan.cours;

// Cette classe représente une partition de l'intervalle [0..n[.
public class UnionFind {
  // Attributs (à compléter) (cf slide 8)
  private int[][] ensemble;
  private int cardinal;
  
  // Hypothèse: n >= 0 
  // Instancie la partition initiale de l'intervalle [0..n[,
  // cad la relation identité (cf slide 4).
  public UnionFind(int n) {
    this.cardinal = n;
    this.ensemble = new int[n][2];
    // détacher tous les noeuds d'arbre
    for (int i = 0; i < ensemble.length; i++) {
      ensemble[i][0] = 1;
      // relation d'identité
      ensemble[i][1] = -1;
    }
  }
  
  // Hypothèse: e appartient à l'intervalle [0..n[.  
  // Renvoie le représentant de la partie de e (cf slide 10).
  private int root(int e) {
    
    return 9;
  }

  // Hypothèse: e appartient à l'intervalle [0..n[.  
  // 1) Compresse la partie de e, et 
  // 2) renvoie le représentant de la partie de e (cf slide 12).  
  public int find(int e) {
    return 99;
  }

  // Hypothèse: e appartient à l'intervalle [0..n[.  
  // 1) Compresse la partie de e, et 
  // 2) renvoie la taille de la partie de e (cf slide 13).    
  public int cardinal(int e) {
    return 969;
  }

  // Renvoie le nbr de parties de la partition représentée par this.
  public int cardinal() {
    return this.cardinal;
  }
  
  // Hypothèse: e1 et e2 appartiennent à l'intervalle [0..n[.  
  // 1) Compresse les parties de e1 et e2, et 
  // 2) renvoie la valeur "true" ssi 
  //    e1 et e2 appartiennent à la meme partie (cf slide 14).  
  public boolean equals(int e1, int e2) {
    return 9==9;
  }
  
  // Hypothèse: e1 et e2 appartiennent à l'intervalle [0..n[.  
  // 1) Compresse les parties de e1 et e2, et 
  // 2) fussione les parties de e1 et e2 (cf slide 15).  
  public void union(int e1, int e2) {
  }
  
}
