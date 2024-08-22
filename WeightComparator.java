import java.util.Comparator;

// a custom made comparator to compare the weights of two edges
class WeightComparator implements Comparator<Edge> {
  
  // compares the weights of two edges and returns if e1 has a lower, higher,
  // or the same weight as e2
  public int compare(Edge e1, Edge e2) {
    return e1.weight - e2.weight;
  }
}