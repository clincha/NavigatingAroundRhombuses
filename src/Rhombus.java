import java.util.List;
import java.util.stream.Collectors;

public class Rhombus {

  private List<Vertex> vertices;

  Rhombus(List<Vertex> vertices) {
    this.vertices = vertices;
  }

  List<Vertex> getVertices() {
    return vertices;
  }

  void setVertices(List<Vertex> vertices) {
    this.vertices = vertices;
  }

  Line[] getLines() {
    Line[] lines = new Line[4];
    for (int i = 0; i < 4; i++) {
      lines[i] = new Line(vertices.get(i), vertices.get((i + 1) % 4));
    }
    return lines;
  }

  @Override
  public String toString() {
    return "(" +
      vertices.stream()
        .map(Vertex::toString)
        .collect(Collectors.joining(", ")) +
      ")";
  }
}
