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

  @Override
  public String toString() {
    return "Rhombus{" +
      "vertices=" + Arrays.toString(vertices) +
      '}';
  }
}
