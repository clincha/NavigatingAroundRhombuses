import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class JourneyPlannerTest {

  JourneyPlanner journeyPlanner;

  @BeforeEach
  public void setup() {
    journeyPlanner = new JourneyPlanner();
  }

  @Test
  public void nextConfigsTest() {
    List<Vertex> vertices = journeyPlanner.nextConfigs(new Vertex(18, 2));
    assertFalse(vertices.isEmpty());
  }

  @Test
  public void testSpecificPuzzle() {
    List<Line> puzzles = Loader.getPuzzles();
    LinkedList<Vertex> route = journeyPlanner.iterativeDeepening(puzzles.get(0).getStart(), puzzles.get(0).getEnd());
    assertFalse(route.isEmpty());
  }

  @Test
  public void AreStartAndFinishCorrect() {
    List<Line> puzzlesArray = Loader.getPuzzles();
    for (Line puzzle : puzzlesArray) {
      LinkedList<Vertex> route = journeyPlanner.iterativeDeepening(puzzle.getStart(), puzzle.getEnd());
      assertEquals(puzzle.getStart(), route.get(0));
      assertEquals(puzzle.getEnd(), route.get(route.size() - 1));
    }
  }

  @Test
  public void linesIntersectTest() {
    Line line = new Line(new Vertex(3, 6), new Vertex(10, 9));
    Line line1 = new Line(new Vertex(9, 9), new Vertex(11, 6));
    assertTrue(Vertex.linesIntersect(line.getStart(), line.getEnd(), line1.getStart(), line1.getEnd()));
  }
}