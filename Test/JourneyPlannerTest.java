import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class JourneyPlannerTest {

  private static final int PUZZLE_LENGTH = 12;

  JourneyPlanner journeyPlanner;

  @BeforeEach
  public void setup() {
    journeyPlanner = new JourneyPlanner();
  }

  @Test
  public void AreStartAndFinishCorrect() throws IOException {
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

    for (int i = 0; i < PUZZLE_LENGTH; i++) {
      Vertex start = startVertices.get(i);
      Vertex finish = endVertices.get(i);

      LinkedList<Vertex> route = journeyPlanner.iterativeDeepening(start, finish);

      System.out.println(route);

      assertEquals(start, route.get(0));
      assertEquals(finish, route.get(route.size() - 1));
    }
  }

  @Test
  public void vertexWithinRhombusTest() {
    Rhombus rhombus = getRhombus();

    Vertex vertex = new Vertex(20, 16);
    assertTrue(vertex.within(rhombus));

    vertex = new Vertex(18, 16);
    assertFalse(vertex.within(rhombus));
  }

  private Rhombus getRhombus() {
    Vertex[] vertices = new Vertex[4];
    vertices[0] = new Vertex(19, 10);
    vertices[1] = new Vertex(21, 10);
    vertices[2] = new Vertex(21, 18);
    vertices[3] = new Vertex(19, 18);
    return new Rhombus(vertices);
  }

  @Test
  public void linesIntersectTest() {
    Line line = new Line(new Vertex(19, 18), new Vertex(14, 15));
    Line line1 = new Line(new Vertex(11, 16), new Vertex(20, 16));
    assertTrue(Vertex.linesIntersect(line.getStart(), line.getEnd(), line1.getStart(), line1.getEnd()));
  }
}