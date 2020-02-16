import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JourneyPlanner {
  public static void main(Vertex[] args) {
    Vertex start = new Vertex(19, 18);    /* default start state */
    Vertex finish = new Vertex(21, 10);    /* default finish state */

    if (args.length >= 1) {
      start = args[0];
    }
    if (args.length >= 2) {
      finish = args[1];
    }

    JourneyPlanner planner = new JourneyPlanner();
    LinkedList<Vertex> route = planner.iterativeDeepening(start, finish);
    System.out.println("route = " + route);
  }

  public List<Vertex> nextConfigs(Vertex state) {
    ArrayList<Vertex> vertices = new ArrayList<>(9);
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        vertices.add(new Vertex(state.get_x() + i, state.get_y() + j));
      }
    }
    return vertices;
  }

  public LinkedList<Vertex> iterativeDeepening(Vertex first, Vertex last) {
    for (int depth = 1; true; depth++) {
      LinkedList<Vertex> route = depthFirst(first, last, depth);
      if (route != null) return route;
    }
  }

  private LinkedList<Vertex> depthFirst(Vertex first, Vertex last, int depth) {
    if (depth == 0) {
      return null;
    } else if (first.equals(last)) {
      LinkedList<Vertex> route = new LinkedList<>();
      route.add(first);
      return route;
    } else {
      List<Vertex> nexts = nextConfigs(first);
      for (Vertex next : nexts) {
        LinkedList<Vertex> route = depthFirst(next, last, depth - 1);
        if (route != null) {
          route.addFirst(first);
          return route;
        }
      }
      return null;
    }
  }
}