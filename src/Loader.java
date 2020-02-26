import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Loader {

  public static List<Rhombus> getRhombuses() {
    List<Rhombus> rhombuses = new ArrayList<>();

    try {
      File obstacles = new File("data/rhombus.csv");
      BufferedReader bufferedReader = new BufferedReader(new FileReader(obstacles));
      for (String line : bufferedReader.lines()
        .collect(Collectors.toList())) {
        Matcher matcher = Pattern.compile("\\((\\d+), (\\d+)\\), \\((\\d+), (\\d+)\\), \\((\\d+), (\\d+)\\), \\((\\d+), (\\d+)\\)").matcher(line);
        if (matcher.find()) {
          List<Vertex> vertices = new ArrayList<>(4);
          for (int i = 1; i < matcher.groupCount() / 2; i = i + 2) {
            vertices.add(new Vertex(Integer.parseInt(matcher.group(i)), Integer.parseInt(matcher.group(i + 1))));
          }
          rhombuses.add(new Rhombus(vertices));
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return rhombuses;
  }

  static List<Line> getPuzzles() {
    List<Line> puzzlesArray = new ArrayList<>();

    try {
      File puzzles = new File("data/puzzles.csv");
      BufferedReader bufferedReader = new BufferedReader(new FileReader(puzzles));
      for (String line : bufferedReader.lines()
        .collect(Collectors.toList())) {
        Matcher matcher = Pattern.compile("\\((\\d+), (\\d+)\\) --> \\((\\d+), (\\d+)\\)").matcher(line);
        if (matcher.find()) {
          puzzlesArray.add(new Line(
            new Vertex(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
            new Vertex(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))));
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return puzzlesArray;
  }
}