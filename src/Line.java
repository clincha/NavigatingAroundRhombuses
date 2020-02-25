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

  @Override
  public String toString() {
    return start + " --> " + end;
  }
}
