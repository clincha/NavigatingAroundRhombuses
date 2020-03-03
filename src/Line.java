public class Line {

  private Vertex start;
  private Vertex end;

  public Line(Vertex start, Vertex end) {
    this.start = start;
    this.end = end;
  }

  public Vertex getStart() {
    return start;
  }

  public void setStart(Vertex start) {
    this.start = start;
  }

  public Vertex getEnd() {
    return end;
  }

  public void setEnd(Vertex end) {
    this.end = end;
  }

  public boolean crossesThrough(Rhombus rhombus) {
    Line line1 = new Line(rhombus.getVertices().get(0), rhombus.getVertices().get(2));
    Line line2 = new Line(rhombus.getVertices().get(1), rhombus.getVertices().get(3));
    return Vertex.linesIntersect(this.getStart(), this.getEnd(), line1.getStart(), line1.getEnd()) ||
      Vertex.linesIntersect(this.getStart(), this.getEnd(), line2.getStart(), line2.getEnd());
  }

  @Override
  public String toString() {
    return start + " --> " + end;
  }
}
