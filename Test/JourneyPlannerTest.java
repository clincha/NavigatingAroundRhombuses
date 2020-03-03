import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class JourneyPlannerTest {

  JourneyPlanner journeyPlanner;

  @BeforeEach
  public void setup() {
    journeyPlanner = new JourneyPlanner();
  }

  @Test
  public void nextConfigsTest() {
    List<Vertex> vertices = journeyPlanner.nextConfigs(new Vertex(18, 2));
    assertEquals(
      List.of(
        new Vertex(17, 6),
        new Vertex(21, 2),
        new Vertex(13, 2),
        new Vertex(5, 2),
        new Vertex(10, 2)),
      vertices);
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
}