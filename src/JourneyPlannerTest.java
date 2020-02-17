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

    File puzzles = new File("src/puzzles.csv");
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
  public void test() {
    Vertex vertex = new Vertex(0, 0);
    Vertex vertex2 = new Vertex(2, 2);
    Vertex point = new Vertex(0, 2);
    Vertex point2 = new Vertex(2, 0);

    System.out.println(Vertex.linesIntersect(point, point2, vertex, vertex2));
  }

}