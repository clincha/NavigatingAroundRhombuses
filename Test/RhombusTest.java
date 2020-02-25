import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

class RhombusTest {

  @Test
  public void whaterver(){
    Line line = new Line(new Vertex(2,8), new Vertex(5,2));
    Line line2 = new Line(new Vertex(2,6), new Vertex(10,9));

    assertTrue(Vertex.linesIntersect(line.getStart(), line.getEnd(), line2.getStart(), line2.getEnd()));
  }

  @Test
  public void getLinesTest() {
    ArrayList<Vertex> vertices = new ArrayList<>(4);
    vertices.add(new Vertex(0, 1));
    vertices.add(new Vertex(1, 1));
    vertices.add(new Vertex(1, 0));
    vertices.add(new Vertex(0, 0));
    Rhombus rhombus = new Rhombus(vertices);

    assertSame(rhombus.getLines()[0].getStart(), vertices.get(0));
    assertSame(rhombus.getLines()[0].getEnd(), vertices.get(1));
    assertSame(rhombus.getLines()[0].getEnd(), vertices.get(1));
    assertSame(rhombus.getLines()[1].getStart(), vertices.get(1));
  }
}