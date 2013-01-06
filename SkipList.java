import java.util.*;
import java.util.logging.*;

public class SkipList {

  private Node head;

  private int numElements;

  private static Logger logger = Logger.getLogger("SkipList");

  public SkipList() {
    head = new Node();
    numElements = 0;
  }

  public void insert(int key) {
    int maxLevels = (int)Math.ceil(Math.log(numElements + 1) / Math.log(2.0));
    int levels = randomLevels(maxLevels);

    head.growToLevel(levels);
    Node newNode = new Node();
    newNode.setKey(key);
    newNode.growToLevel(levels);
    Node node = head;
    for (int level = levels - 1; level >= 0; level--) {
      node = floor(node, level, key);
      // TODO: Detect and handle duplicate here.
      // if (node.getKey() == key) { ... }
      newNode.setNextOnLevel(level, node.getNextOnLevel(level));
      node.setNextOnLevel(level, newNode);
    }
    numElements++;
  }

  public boolean find(int key) {
    int levels = head.getLevels();
    Node node = head;
    for (int level = levels - 1; level >= 0; level--) {
      node = floor(node, level, key);
      Node next = node.getNextOnLevel(level);
      if (next != null && next.getKey() == key) {
        return true;
      }
    }
    return false;
  }

  public void remove(int key) {
    boolean found = false;
    int levels = head.getLevels();
    Node node = head;
    for (int level = levels - 1; level >= 0; level--) {
      node = floor(node, level, key);
      Node next = node.getNextOnLevel(level);
      if (next != null && next.getKey() == key) {
        found = true;
        node.setNextOnLevel(level, next.getNextOnLevel(level));
      }
    }
    if (found) {
      numElements--;
    }
  }

  public Node floor(Node start, int level, int key) {
    // Should ensure start.getKey() < key.
    Node node = start;
    Node next = node.getNextOnLevel(level);
    while (next != null && next.getKey() < key) {
      node = next;
      next = node.getNextOnLevel(level);
    }
    return node;
  }

  public static int randomLevels(int maxLevels) {
    double rand = Math.random();
    int levels = 1;
    double probabilityToLevel = Math.pow(0.5, (double)levels);
    while (levels < maxLevels && probabilityToLevel < rand) {
      ++levels;
      probabilityToLevel += Math.pow(0.5, (double)levels);
    }
    return levels;
  }

  public String toString() {
    String result = "SkipList content: [ ";
    Node node = head.getNextOnLevel(0);
    while (node != null) {
      result += node.key + ", ";
      node = node.getNextOnLevel(0);
    }
    result += "]";
    return result;
  }

  public void testRandomLevels() {
    for (int i = 1; i < 5; ++i) {
      logger.info("maxLevel = " + i + ", randomLevel = " + SkipList.randomLevels(i));
    }
  }

  public static void main(String[] args) {
    SkipList skipList = new SkipList();

    skipList.testRandomLevels();

    skipList.insert(3);
    skipList.insert(7);
    skipList.insert(13);
    skipList.insert(14);
    skipList.insert(4);
    skipList.insert(12);
    skipList.insert(17);
    logger.info(skipList.toString());

    logger.info("Find 1: " + skipList.find(1));
    logger.info("Find 3: " + skipList.find(3));
    logger.info("Find 13: " + skipList.find(13));
    logger.info("Find 15: " + skipList.find(15));
    logger.info("Find 19: " + skipList.find(19));

    skipList.remove(13);
    logger.info("Find 13 after it is removed: " + skipList.find(13));
  }
}
