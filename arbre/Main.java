package arbre;

public class Main {

  public static void main(String[] args) {
    Tree l1 = new Tree(1);
    Tree l3 = new Tree(3);
    Tree l7 = new Tree(7);

    Tree t2 = new Tree(2, l1, l3);
    Tree t6 = new Tree(6, l7);

    Tree t4 = new Tree(4, t2, t6);

    System.out.println("Exemple 1 → # feuilles = " + Trees.nbrLeaves(t4));

    Tree[] ls = Trees.flattenLeaves(t4);

    System.out.println("Exemple 2 → Les " + ls.length + " feuilles");
    for (Tree flattenLeave : ls) {
      System.out.println(flattenLeave.value);
    }
    System.out.println("Exemple 1 → Path V1");
    Trees.pathV1(l7);

    System.out.println("Exemple 2 → Path V2");
    Trees.pathV2(l7);

    System.out.println("Exercice 1.1 → #noeuds = " + Trees.nbrNodes(t4));
    System.out.println("Exercice 1.2 → min = " + Trees.min(t4));
    System.out.println("Exercice 1.3 → min = " + Trees.min(t4));
    System.out.println("Exercice 1.4 → equals = " + Trees.equals(t4, t4));
    System.out.println("Exercice 1.5 → depth = " + Trees.depth(l7));
    System.out.println("Exercice 1.6 → sameOne = " + Trees.sameOne(t6, t4));
    System.out.println("Exercice 1.7 → dfsPrint = ");
    Trees.dfsPrint(t4);
    System.out.println("Exercice 1.8 → bfsPrint = ");
    Trees.bfsPrint(t4);
    System.out.println("Exercice 2.1 → printPathV1 = ");
    Trees.printPathV1(l7);
    System.out.println("Exercice 2.2 → printPathV2 = ");
    Trees.printPathV2(l7);
    System.out.println("Exercice 2.3 → printPathV3 = ");
    Trees.printPathV3(t4, 7);
    System.out.println("Exercice 3.1 → toArray = ");
    int[][] tab = Trees.toArray(t4);
    for (int i = 0; i < tab.length; i++) {
      for (int j = 0; j < tab[0].length; j++) {
        System.out.print(tab[i][j] + "\t");
      }
      System.out.println();
    }
    System.out.println("Exercice 3.2 → toTree = " + Trees.equals(Trees.toTree(tab), t4));
    
    System.out.println("Exercice 4.1 → lca = " + Trees.lca(l1, l3).value);
  }

}
