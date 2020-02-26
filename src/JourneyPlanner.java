import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JourneyPlanner {
  private Map<Vertex, List<Vertex>> cache = new HashMap<>();

  public static void main(String[] args) {
    JourneyPlanner journeyPlanner = new JourneyPlanner();
    List<Line> puzzles = Loader.getPuzzles();

    for (int i = 0; i < puzzles.size(); i++) {
      LinkedList<Vertex> vertices = journeyPlanner.iterativeDeepening(puzzles.get(i).getStart(), puzzles.get(i).getEnd());
      try {
        FileWriter writer = new FileWriter("solutions/" + (i + 1) + ".txt");
        writer.write(
          vertices.stream()
            .map(Vertex::toString)
            .collect(Collectors.joining(" ")));
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
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

  public List<Vertex> nextConfigs(Vertex state) {
    if (cache.containsKey(state))
      return cache.get(state);

    List<Vertex> targets = Loader.getRhombuses().stream()
      // Get all the possible targets on the map
      .map(Rhombus::getVertices)
      .flatMap(Collection::stream)
      // Remove duplicate targets
      .distinct()
      .collect(Collectors.toList());

    for (Rhombus rhombus : Loader.getRhombuses()) {
      for (Line line : rhombus.getLines()) {
        targets = targets.stream()
          // Remove the target if the line between state and target intersects the border of the obstacles
          .filter(vertex -> !Vertex.linesIntersect(state, vertex, line.getStart(), line.getEnd()))
          // Remove the target if the line between state and target crosses through the middle of the obstacles
          .filter(vertex -> !new Line(vertex, state).crossInternally(rhombus))
          // Remove the target if we are on the target
          .filter(vertex -> !vertex.equals(state))
          .collect(Collectors.toList());
      }
    }
    cache.put(state, targets);
    return targets;
  }
}