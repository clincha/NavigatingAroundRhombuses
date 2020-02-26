import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JourneyPlanner {
  private static final int RHOMBOIDS = 16;
  private Map<Vertex, List<Vertex>> cache = new HashMap<>();

  public static Rhombus[] getRhombuses() {
    Rhombus[] rhombuses = new Rhombus[RHOMBOIDS];

    File rhombusCSV = new File("data/rhombus.csv");

    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(rhombusCSV));
      Matcher[] matchers = new Matcher[4];
      for (int i = 0; i < matchers.length; i++) {
        matchers[i] = Pattern.compile("(\\d+), (\\d+)").matcher(bufferedReader.readLine());
      }

      for (int i = 0; i < RHOMBOIDS; i++) {
        ArrayList<Vertex> rhombusVertices = new ArrayList<>(4);
        for (int j = 0; j < 4; j++) {
          if (matchers[j].find()) {
            rhombusVertices.add(new Vertex(Integer.parseInt(matchers[j].group(1)), Integer.parseInt(matchers[j].group(2))));
          }
        }
        rhombuses[i] = new Rhombus(rhombusVertices);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return rhombuses;
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

    Rhombus[] rhombuses = getRhombuses();
    List<Vertex> vertices = Arrays.stream(rhombuses)
      .map(Rhombus::getVertices)
      .flatMap(Collection::stream)
      .collect(Collectors.toList());

    for (Rhombus rhombus : rhombuses) {
      for (Line line : rhombus.getLines()) {
        if (Vertex.vertexIntersect(state, line.getStart(), line.getEnd())) {
          vertices.remove(rhombus.getOppositeLine(line).getStart());
          vertices.remove(rhombus.getOppositeLine(line).getEnd());
        }

        vertices = vertices.stream()
          .filter(vertex -> !Vertex.linesIntersect(state, vertex, line.getStart(), line.getEnd()))
//          .filter(vertex -> !vertex.within(rhombus))
//          .filter(vertex -> !Vertex.vertexIntersect(vertex, rhombus.getOppositeLine(line).getStart(), rhombus.getOppositeLine(line).getEnd()))
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