import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LoaderTest {

  @Test
  void getRhombuses() {
    assertFalse(Loader.getRhombuses().isEmpty());
    assertEquals(16, Loader.getRhombuses().size());
  }

  @Test
  void getPuzzles() {
    assertFalse(Loader.getPuzzles().isEmpty());
    assertEquals(12, Loader.getPuzzles().size());
  }
}