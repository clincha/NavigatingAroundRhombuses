import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FormatTest {

  @Test
  public void checkSolutions() {
    try {
      Files.list(new File("solutions").toPath())
        .map(Path::getFileName)
        .map(Path::toString)
        .forEach(file -> FormatChecker.main(new String[]{"solutions/" + file}));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
