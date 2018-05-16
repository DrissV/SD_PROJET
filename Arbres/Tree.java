package arbre;

public class Tree {

  int value;

  Tree parent;

  Tree[] children;

  // *******************************************************
  // CONSTRUCTEURS
  // *******************************************************
  public Tree(int v, Tree... chd) {
    value = v;
    children = chd;
    for (Tree t : chd) {
      t.parent = this;
    }
  }

  public Tree(int v) {
    this(v, new Tree[0]);
  }
}
