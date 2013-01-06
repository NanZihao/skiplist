import java.util.*;

public class Node {

  public int key;

  public List<Node> nextOnLevel;

  public Node() {
    nextOnLevel = new ArrayList<Node>(1);
  }

  public int getKey() {
    return key;
  }

  public void setKey(int newKey) {
    key = newKey;
  }

  public int getLevels() {
    return nextOnLevel.size();
  }

  public void growToLevel(int level) {
    while (nextOnLevel.size() < level) {
      nextOnLevel.add(null);
    }
  }

  public Node getNextOnLevel(int level) {
    if (level < nextOnLevel.size()) {
      return nextOnLevel.get(level);
    } else {
      return null;
    }
  }

  public void setNextOnLevel(int level, Node next) {
    nextOnLevel.set(level, next);
  }

  public static void main(String[] args) {
    Node head = new Node();

    System.out.println("Next is null before inserting anything.");
    System.out.println(head.getNextOnLevel(0));

    System.out.println("Next is still null after growing to more level.");
    head.growToLevel(3);
    System.out.println(head.getNextOnLevel(0));
    System.out.println(head.getNextOnLevel(1));
    System.out.println(head.getNextOnLevel(2));

    System.out.println("Next points to Node after inserting at the level.");
    head.setNextOnLevel(2, new Node());
    System.out.println(head.getNextOnLevel(2));
  }
}
