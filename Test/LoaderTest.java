import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTest {

  @Test
  void getRhombuses() {
    assertFalse(Loader.getRhombuses().isEmpty());
    System.out.println(Loader.getRhombuses());
  }

  @Test
  void getPuzzles() {
    assertFalse(Loader.getPuzzles().isEmpty());
  }
}