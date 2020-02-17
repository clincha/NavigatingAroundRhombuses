import java.util.Arrays;

public class Rhombus {

  private Vertex[] vertices;

  public Rhombus(Vertex[] vertices) {
    this.vertices = vertices;
  }

  public Vertex[] getVertices() {
    return vertices;
  }

  public void setVertices(Vertex[] vertices) {
    this.vertices = vertices;
  }

  public Line[] getLines() {
    Line[] lines = new Line[4];
    for (int i = 0; i < 4; i++) {
      lines[i] = new Line(vertices[i], vertices[(i + 1) % 4]);
    }
    return lines;
  }

  @Override
  public String toString() {
    return "Rhombus{" +
      "vertices=" + Arrays.toString(vertices) +
      '}';
  }
}
