import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {

  private static final int RHOMBOIDS = 16;
  private static final int PUZZLE_LENGTH = 12;

  public static Rhombus[] getRhombuses() {
    Rhombus[] rhombuses = new Rhombus[RHOMBOIDS];

    File rhombusCSV = new File("data/rhombus.csv");

    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(rhombusCSV));
      Matcher[] matchers = new Matcher[4];
      for (int i = 0; i < matchers.length; i++) {
        matchers[i] = Pattern.compile("(\\d+), (\\d+)").matcher(bufferedReader.readLine());
      }

      for (int i = 0; i < RHOMBOIDS; i++) {
        ArrayList<Vertex> rhombusVertices = new ArrayList<>(4);
        for (int j = 0; j < 4; j++) {
          if (matchers[j].find()) {
            rhombusVertices.add(new Vertex(Integer.parseInt(matchers[j].group(1)), Integer.parseInt(matchers[j].group(2))));
          }
        }
        rhombuses[i] = new Rhombus(rhombusVertices);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return rhombuses;
  }

  public static Line[] getPuzzles() {
    try {
      ArrayList<Vertex> startVertices = new ArrayList<>(PUZZLE_LENGTH);
      ArrayList<Vertex> endVertices = new ArrayList<>(PUZZLE_LENGTH);

      File puzzles = new File("data/puzzles.csv");
      BufferedReader bufferedReader = new BufferedReader(new FileReader(puzzles));

      String startLine = bufferedReader.readLine();
      String endLine = bufferedReader.readLine();

      Matcher startMatcher = Pattern.compile("(\\d+), (\\d+)").matcher(startLine);
      Matcher endMatcher = Pattern.compile("(\\d+), (\\d+)").matcher(endLine);

      while (endMatcher.find() && startMatcher.find()) {
        startVertices.add(new Vertex(Integer.parseInt(startMatcher.group(1)), Integer.parseInt(startMatcher.group(2))));
        endVertices.add(new Vertex(Integer.parseInt(endMatcher.group(1)), Integer.parseInt(endMatcher.group(2))));
      }
      return null;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}