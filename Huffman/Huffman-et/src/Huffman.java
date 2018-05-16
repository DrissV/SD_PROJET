import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Huffman {

  private static class Node implements Comparable<Node> {
    private char ch;
    private int freq;
    private final Node left, right;

    public Node(char ch, int freq, Node left, Node right) {
      this.ch = ch;
      this.freq = freq;
      this.left = left;
      this.right = right;
    }

    public boolean isLeaf() {
      return left == null && right == null;
    }

    @Override
    public int compareTo(Node node) {
      return freq - node.freq;
    }

  }

  // renvoie une map qui a comme cl� les lettres de la chaine de
  // caract�re donn�e en param�tre et comme valeur la fr�quence de
  // ces lettres
  public static Map<Character, Integer> computeFreq(String s) {
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    for (char c : s.toCharArray()) {
      if (map.containsKey(c)) {
        map.replace(c, map.get(c) + 1);
      } else {
        map.put(c, 1);
      }
    }
    return map;
  }

  // renvoie l'arbre de Huffman obtenu � partir de la map des fr�quences des lettres
  public static Node buildTree(Map<Character, Integer> freq) {
    PriorityQueue<Node> file = new PriorityQueue<Node>();
    for (Entry<Character, Integer> entry : freq.entrySet()) {
      file.add(new Node(entry.getKey(), entry.getValue(), null, null));
    }
    Node newNode = null;
    while (file.size() > 1) {
      Node first = file.poll(), second = file.poll();
      newNode = new Node(' ', first.freq + second.freq, first, second);
      file.add(newNode);
    }
    return newNode;
  }

  // renvoie une map qui associe chaque lettre � son code (suite de 0 et de 1).
  // Ce code est obtenu en parcourant l'arbre de Huffman donn� en param�tre
  public static Map<Character, String> buildCode(Node root) {
    Map<Character, String> map = new HashMap<Character, String>();
    buildCode(root, "", map);
    return map;
  }

  private static void buildCode(Node root, String s, Map<Character, String> map) {
    if (!root.isLeaf()) {
      buildCode(root.left, s + 0, map);
      buildCode(root.right, s + 1, map);
    } else {
      map.put(root.ch, s);
    }
  }


  // encode la chaine de caract�re prise en param�tre en une chaine de
  // bit (0 et 1) en utilisant la map des codes codeMap
  public static String compress(String s, Map<Character, String> codeMap) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
      sb.append(codeMap.get(c));
    }
    return sb.toString();
  }

  // Cette m�thode d�code une chaine de 0 et de 1 cod� � l'aide de l'algorithme de Huffman
  // En param�tre, en plus de la chaine � d�coder, est sp�cifi� la racine de l'arbre de
  // Huffman
  public static String expand(Node root, String t) {
    StringBuilder s = new StringBuilder();
    int freqRoot = root.freq, compteur = 0;
    Node courant = root;
    for (char c : t.toCharArray()) {
     if (compteur == freqRoot) {
      break;
     }
     if (courant.isLeaf()) {
      s.append(courant.ch);
      courant = root;
      compteur++;
     }
     if (c == '1') {
      courant = courant.right;
     } else {
      courant = courant.left;
     }
    }
    return s.toString();
  }

  public static void main(String[] args) throws IOException {
    String s = HuffmanReadFile.loadFile(new File("11-0.txt"));
    Map<Character, Integer> freq = computeFreq(s);
    Node root = buildTree(freq);
    Map<Character, String> code = buildCode(root);
    String compress = compress(s, code);
    HuffmanWriteFile.write("fichier_compresse", compress);
    String texteOriginal = expand(root, HuffmanReadFile.read("fichier_compresse"));
    System.out.println(texteOriginal);
  }

}
