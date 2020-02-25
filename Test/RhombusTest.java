import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertSame;

class RhombusTest {

  @Test
  public void getLinesTest() {
    Vertex[] vertices = new Vertex[4];
    vertices[0] = new Vertex(0, 0);
    vertices[1] = new Vertex(0, 1);
    vertices[2] = new Vertex(1, 1);
    vertices[3] = new Vertex(1, 0);
    Rhombus rhombus = new Rhombus(vertices);

    assertSame(rhombus.getLines()[0].getStart(), vertices[0]);
    assertSame(rhombus.getLines()[0].getEnd(), vertices[1]);
    assertSame(rhombus.getLines()[0].getEnd(), vertices[1]);
    assertSame(rhombus.getLines()[1].getStart(), vertices[1]);
  }
}