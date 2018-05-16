// Cette classe représente une partition de l'intervalle [0..n[.
public class UnionFind {
  // Attributs (à compléter) (cf slide 8)
  private int[][] tab;
  private int cardinal;

  // Hypothèse: n >= 0
  // Instancie la partition initiale de l'intervalle [0..n[,
  // cad la relation identité (cf slide 4).
  public UnionFind(int n) {
    this.cardinal = n;
    this.tab = new int[n][2];
    for (int i = 0; i < n; i++) {
      tab[i][0] = 1;
      tab[i][1] = -1;
    }
  }

  // Hypothèse: e appartient à l'intervalle [0..n[.
  // Renvoie le représentant de la partie de e (cf slide 10).
  private int root(int e) {
    if (tab[e][1] != -1) {
      e = root(tab[e][1]);
    }
    return e;
  }

  // Hypothèse: e appartient à l'intervalle [0..n[.
  // 1) Compresse la partie de e, et
  // 2) renvoie le représentant de la partie de e (cf slide 12).
  public int find(int e) {
    int root = root(e), parentE = tab[e][1], taille;
    while (parentE != -1) {
      taille = tab[e][0];
      tab[parentE][0] -= taille;
      tab[e][1] = root;
      e = parentE;
      parentE = tab[e][1];
    }
    return root;
  }

  // Hypothèse: e appartient à l'intervalle [0..n[.
  // 1) Compresse la partie de e, et
  // 2) renvoie la taille de la partie de e (cf slide 13).
  public int cardinal(int e) {
    return tab[find(e)][0];
  }

  // Renvoie le nbr de parties de la partition représentée par this.
  public int cardinal() {
    return this.cardinal;
  }

  // Hypothèse: e1 et e2 appartiennent à l'intervalle [0..n[.
  // 1) Compresse les parties de e1 et e2, et
  // 2) renvoie la valeur "true" ssi
  // e1 et e2 appartiennent à la meme partie (cf slide 14).
  public boolean equals(int e1, int e2) {
    int rootE1 = find(e1), rootE2 = find(e2);
    return rootE1 == rootE2;
  }

  // Hypothèse: e1 et e2 appartiennent à l'intervalle [0..n[.
  // 1) Compresse les parties de e1 et e2, et
  // 2) fussione les parties de e1 et e2 (cf slide 15).
  public void union(int e1, int e2) {
    int rootE1 = find(e1), rootE2 = find(e2), tailleRootE1 = cardinal(e1),
        tailleRootE2 = cardinal(e2);
    if (!equals(e1, e2)) {
      if (tailleRootE1 < tailleRootE2) {
        tab[rootE2][0] += tailleRootE1;
        tab[rootE1][1] = rootE2;
      } else {
        tab[rootE1][0] += tailleRootE2;
        tab[rootE2][1] = rootE1;
      }
      cardinal--;
    }
  }

}
