package UnionFind;

public class MainUF {
  public static void main(String[] args) {
    UnionFind uf = new UnionFind(1000);
    uf.union(10, 100);
    System.out.println(uf.find(100));
  }
}
