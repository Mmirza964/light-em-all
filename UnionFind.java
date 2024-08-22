import java.util.HashMap;

// represents a UnionFind data type
class UnionFind {
  HashMap<GamePiece, GamePiece> parent;
  
  UnionFind() {
    this.parent = new HashMap<>();
  }

  // adds a node to a set and links the node back to itself
  void makeSet(GamePiece node) {
    parent.put(node, node);
  }

  // finds the root of the node
  GamePiece find(GamePiece node) {
    GamePiece root = node;
    while (root != parent.get(root)) {
      root = parent.get(root);
    }
    while (node != root) {
      GamePiece next = parent.get(node);
      parent.put(node, root);
      node = next;
    }
    return root;
  }

  // merges two nodes into one set
  void union(GamePiece node1, GamePiece node2) {
    GamePiece root1 = find(node1);
    GamePiece root2 = find(node2);
    if (root1 != root2) {
      parent.put(root1, root2);
    }
  }
}
