import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JourneyPlanner {
  private Map<Vertex, List<Vertex>> cache = new HashMap<>();

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

  public List<Vertex> nextConfigs(Vertex state) {
    if (cache.containsKey(state))
      return cache.get(state);

    List<Rhombus> obstacles = Loader.getRhombuses();
    List<Vertex> vertices = obstacles.stream()
      .map(Rhombus::getVertices)
      .flatMap(Collection::stream)
      .collect(Collectors.toList());

    for (Rhombus rhombus : obstacles) {
      for (Line line : rhombus.getLines()) {
        vertices = vertices.stream()
          .filter(vertex -> !Vertex.linesIntersect(state, vertex, line.getStart(), line.getEnd()))
          .filter(vertex -> !rhombus.passWithin(new Line(vertex, state)))
          .filter(vertex -> !vertex.equals(state))
          .collect(Collectors.toList());
      }
    }
    vertices = new ArrayList<>(new HashSet<>(vertices));
    cache.put(state, vertices);
    return vertices;
  }
}