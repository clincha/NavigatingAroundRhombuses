import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class JourneyPlannerTest {

  private static final int PUZZLE_LENGTH = 12;

  JourneyPlanner journeyPlanner;

  @BeforeEach
  public void setup() {
    journeyPlanner = new JourneyPlanner();
  }

  @Test
  public void nextConfigsTest() {
    List<Vertex> vertices = journeyPlanner.nextConfigs(new Vertex(18, 2));
    System.out.println(vertices);

    //[(17, 6), (13, 2), (10, 2), (14, 7), (11, 6), (21, 2), (5, 2)]
  }

  @Test
  public void testSpecificPuzzle() throws IOException {
    ArrayList<Vertex> startVertices = new ArrayList<>(PUZZLE_LENGTH);
    ArrayList<Vertex> endVertices = new ArrayList<>(PUZZLE_LENGTH);

    File puzzles = new File("data/puzzles.csv");
    BufferedReader bufferedReader = new BufferedReader(new FileReader(puzzles));

    String startLine = bufferedReader.readLine();
    String endLine = bufferedReader.readLine();

    Matcher startMatcher = Pattern.compile("(\\d+), (\\d+)").matcher(startLine);
    Matcher endMatcher = Pattern.compile("(\\d+), (\\d+)").matcher(endLine);

    while (endMatcher.find() && startMatcher.find()) {
      startVertices.add(new Vertex(Integer.parseInt(startMatcher.group(1)), Integer.parseInt(startMatcher.group(2))));
      endVertices.add(new Vertex(Integer.parseInt(endMatcher.group(1)), Integer.parseInt(endMatcher.group(2))));
    }
    Vertex start = startVertices.get(2);
    Vertex finish = endVertices.get(2);

    LinkedList<Vertex> route = journeyPlanner.iterativeDeepening(start, finish);

    System.out.println(route);
  }


  @Test
  public void AreStartAndFinishCorrect() throws IOException {
    List<Line> puzzlesArray = new ArrayList<>(PUZZLE_LENGTH);

    File puzzles = new File("data/puzzles-formatted.csv");
    BufferedReader bufferedReader = new BufferedReader(new FileReader(puzzles));

    for (String line : bufferedReader.lines()
      .collect(Collectors.toList())) {
      Matcher matcher = Pattern.compile("\\((\\d+), (\\d+)\\) --> \\((\\d+), (\\d+)\\)").matcher(line);
      if (matcher.find()) {
        puzzlesArray.add(new Line(
          new Vertex(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
          new Vertex(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))));
      }
    }

    for (Line puzzle : puzzlesArray) {
      LinkedList<Vertex> route = journeyPlanner.iterativeDeepening(puzzle.getStart(), puzzle.getEnd());
      System.out.println(route);
      assertEquals(puzzle.getStart(), route.get(0));
      assertEquals(puzzle.getEnd(), route.get(route.size() - 1));
    }
  }

  @Test
  public void vertexWithinRhombusTest() {
    Rhombus[] rhombuses = Loader.getRhombuses();
    boolean within = false;

    Vertex inside = new Vertex(15, 12);

    for (Rhombus rhombus : rhombuses) {
      if (inside.within(rhombus))
        within = true;
    }
    assertTrue(within);
  }

  private Rhombus getRhombus() {
    ArrayList<Vertex> vertices = new ArrayList<>(4);
    vertices.add(new Vertex(19, 10));
    vertices.add(new Vertex(21, 10));
    vertices.add(new Vertex(21, 18));
    vertices.add(new Vertex(19, 18));
    return new Rhombus(vertices);
  }

  @Test
  public void linesIntersectTest() {
    Line line = new Line(new Vertex(3, 6), new Vertex(10, 9));
    Line line1 = new Line(new Vertex(9, 9), new Vertex(11, 6));
    assertTrue(Vertex.linesIntersect(line.getStart(), line.getEnd(), line1.getStart(), line1.getEnd()));
  }
}