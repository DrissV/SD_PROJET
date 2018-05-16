package arbre;

import java.util.ArrayDeque;
import java.util.Deque;

public class Trees {
  // *******************************************************
  // Un premier exemple: le nombre de feuilles d'un arbre
  // *******************************************************
  public static int nbrLeaves(Tree t) {
    int r;
    if (t.children.length == 0) {
      r = 1;
    } else {
      r = 0;
      for (Tree tChild : t.children) {
        r += nbrLeaves(tChild);
      }
    }
    return r;
  }


  // *******************************************************
  // Un deuxième exemple: aplanir un arbre
  // *******************************************************
  public static Tree[] flattenLeaves(Tree t) {
    int nl = nbrLeaves(t);
    Tree[] r = new Tree[nl];
    flattenLeaves(t, r, 0);
    return r;
  }

  private static int flattenLeaves(Tree t, Tree[] a, int idx) {
    int r;
    if (t.children.length == 0) {
      a[idx] = t;
      r = 1;
    } else {
      r = 0;
      for (Tree tChild : t.children) {
        r += flattenLeaves(tChild, a, idx + r);
      }
    }
    return r;
  }

  // *******************************************************
  // Un troisi�me exemple:
  // tous les algorithmes ne sont pas récursifs
  // *******************************************************
  public static void pathV1(Tree t) {
    System.out.println(t.value);
    if (t.parent != null) {
      pathV1(t.parent);
    }
  }

  public static void pathV2(Tree t) {
    while (t != null) {
      System.out.println(t.value);
      t = t.parent;
    }
  }

  // *******************************************************
  // Exercices 1
  // *******************************************************

  // 1.1)
  public static int nbrNodes(Tree t) {
    int nbrNodes = 1;
    for (Tree tChild : t.children) {
      nbrNodes += nbrNodes(tChild);
    }
    return nbrNodes;
  }

  // 1.2)
  public static int min(Tree t) {
    int min = t.value;
    for (Tree tChild : t.children) {
      int minChild = min(tChild);
      if (minChild < min) {
        min = minChild;
      }
    }
    return min;
  }

  // 1.3)
  public static int sum(Tree t) {
    int sum = t.value;
    for (Tree tChild : t.children) {
      sum += sum(tChild);
    }
    return sum;
  }

  // 1.4)
  public static boolean equals(Tree t1, Tree t2) {
    if (t1.value != t2.value || t1.children.length != t2.children.length) {
      return false;
    }
    for (int i = 0; i < t1.children.length; i++) {
      if (!equals(t1.children[i], t2.children[i])) {
        return false;
      }
    }
    return true;
  }

  // 1.5)
  public static int depth(Tree n) {
    return (n.parent != null) ? 1 + depth(n.parent) : 0;
  }

  // 1.6)
  public static boolean sameOne(Tree n1, Tree n2) {
    if (n2.parent != null) {
      return sameOne(n1, n2.parent);
    }
    if (n1.parent != null) {
      return sameOne(n1.parent, n2);
    }
    return n1 == n2;
  }

  // 1.7)
  public static void dfsPrint(Tree t) {
    System.out.println(t.value);
    for (Tree tChild : t.children) {
      dfsPrint(tChild);
    }
  }

  // 1.8)
  public static void bfsPrint(Tree t) {
    Deque<Tree> file = new ArrayDeque<Tree>();
    file.push(t);
    bfsPrint(file);
  }

  private static void bfsPrint(Deque<Tree> file) {
    if (!file.isEmpty()) {
      Tree t = file.pop();
      System.out.println(t.value);
      for (Tree tChild : t.children) {
        file.addLast(tChild);
      }
      bfsPrint(file);
    }
  }

  // *******************************************************
  // Exercices 2
  // *******************************************************

  // 2.1)
  public static void printPathV1(Tree node) {
    if (node.parent != null) {
      printPathV1(node.parent);
    }
    System.out.println(node.value);
  }

  // 2.2)
  public static void printPathV2(Tree node) {
    int depth = depth(node);
    Tree[] parents = new Tree[depth + 1];
    while (node != null) {
      parents[depth] = node;
      node = node.parent;
      depth--;
    }
    for (Tree tParent : parents) {
      System.out.println(tParent.value);
    }
  }

  // 2.3
  public static void printPathV3(Tree t, int v) {
    printPathV3(t, v, false);
  }

  private static boolean printPathV3(Tree t, int v, boolean r) {
    if (t.value == v) {
      printPathV1(t);
      r = true;
    } else {
      for (int i = 0; i != t.children.length && !r; i++) {
        r = printPathV3(t.children[i], v, r);
      }
    }
    return r;
  }

  // *******************************************************
  // Exercices 3
  // *******************************************************

  // 3.1
  public static int[][] toArray(Tree t) {
    int[][] tab = new int[nbrNodes(t)][3];
    toArray(t, tab, 0);
    return tab;
  }

  private static int toArray(Tree t, int[][] tab, int idx) {
    int r = idx;
    tab[idx][0] = t.value;
    int tLength = t.children.length;
    tab[idx][2] = -1;
    if (tLength == 0) {
      tab[idx][1] = -1;
    } else {
      tab[idx][1] = idx + 1;
      toArray(t.children[0], tab, idx + 1);
      r = r + nbrNodes(t.children[0]);
      if (tLength == 2) {
        tab[idx][2] = r + 1;
        toArray(t.children[1], tab, tab[idx][2]);
      }
    }
    return r;
  }

  // 3.2
  public static Tree toTree(int[][] t) {
    return toTree(t, 0);
  }

  private static Tree toTree(int[][] t, int idx) {
    Tree[] children = new Tree[] {};
    if (t[idx][1] != -1 && t[idx][2] != -1) {
      children = new Tree[] {toTree(t, t[idx][1]), toTree(t, t[idx][2])};
    } else if (t[idx][2] == -1 && t[idx][1] != -1) {
      children = new Tree[] {toTree(t, t[idx][1])};
    } else if (t[idx][1] == -1 && t[idx][2] != -1) {
      children = new Tree[] {toTree(t, t[idx][2])};
    }
    return new Tree(t[idx][0], children);
  }

  // *******************************************************
  // Exercices 4
  // *******************************************************

  public static Tree lca(Tree n1, Tree n2) {
    return lca(n1, n2, depth(n1), depth(n2));
  }

  private static Tree lca(Tree n1, Tree n2, int depthN1, int depthN2) {
    if (n1 == n2) {
      return n1;
    }
    if (depthN1 < depthN2) {
      return lca(n1, n2.parent, depthN1, depthN2 - 1);
    } else {
      return lca(n1.parent, n2, depthN1 - 1, depthN2);
    }
  }
}
