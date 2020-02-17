import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JourneyPlanner {
  private static final int RHOMBOIDS = 16;

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
    Rhombus[] rhombuses = getRhombuses();
    List<Vertex> vertices = Arrays.stream(rhombuses).map(Rhombus::getVertices).flatMap(Stream::of).collect(Collectors.toList());

    for (Rhombus rhombus : rhombuses) {
      for (Line line : rhombus.getLines()) {
        vertices = vertices.stream()
          .filter(vertex -> !Vertex.linesIntersect(state, vertex, line.getStart(), line.getEnd()))
          .collect(Collectors.toList());
      }
    }
    return vertices;
  }

  private Rhombus[] getRhombuses() {
    Rhombus[] rhombuses = new Rhombus[RHOMBOIDS];

    File rhombusCSV = new File("src/rhombus.csv");

    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(rhombusCSV));
      Matcher[] matchers = new Matcher[4];
      for (int i = 0; i < matchers.length; i++) {
        matchers[i] = Pattern.compile("(\\d+), (\\d+)").matcher(bufferedReader.readLine());
      }

      for (int i = 0; i < RHOMBOIDS; i++) {
        Vertex[] rhombusVertices = new Vertex[4];
        for (int j = 0; j < 4; j++) {
          if (matchers[j].find()) {
            rhombusVertices[j] = new Vertex(Integer.parseInt(matchers[j].group(1)), Integer.parseInt(matchers[j].group(2)));
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
}