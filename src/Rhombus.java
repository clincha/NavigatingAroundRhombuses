import java.util.ArrayList;
import java.util.List;

public class Rhombus {

  private List<Vertex> vertices;

  public Rhombus(List<Vertex> vertices) {
    this.vertices = vertices;
  }

  public List<Vertex> getVertices() {
    return vertices;
  }

  public void setVertices(List<Vertex> vertices) {
    this.vertices = vertices;
  }

  public Line[] getLines() {
    Line[] lines = new Line[4];
    for (int i = 0; i < 4; i++) {
      lines[i] = new Line(vertices.get(i), vertices.get((i + 1) % 4));
    }
    return lines;
  }

  @Override
  public String toString() {
    return "Rhombus{" +
      "vertices=" + vertices +
      '}';
  }

  public Line getOppositeLine(Line line) {
    List<Vertex> vertices = new ArrayList<>(4);
    vertices.addAll(this.vertices);
    vertices.remove(line.getStart());
    vertices.remove(line.getEnd());
    return new Line(vertices.get(0), vertices.get(1));
  }

  public boolean passWithin(Line line) {
    Line line1 = new Line(vertices.get(0), vertices.get(2));
    Line line2 = new Line(vertices.get(1), vertices.get(3));
    return Vertex.linesIntersect(line.getStart(), line.getEnd(), line1.getStart(), line1.getEnd()) ||
      Vertex.linesIntersect(line.getStart(), line.getEnd(), line2.getStart(), line2.getEnd());
  }
}
