package com.tetris.unittests.unit.things;

import com.tetris.things.Square;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
  private int x = 4;
  private Square b = new Square(x, Color.red);

  @Test
  void getX() {
    assertEquals(x, b.getX());
  }

  @Test
  void getColor() {
    assertEquals(Color.red, b.getColor());
  }

  @Test
  void testClone() throws CloneNotSupportedException {
    Square bClone = b.clone();

    assertAll(
        () -> {
          assertEquals(b.getX(), bClone.getX());
          assertEquals(b.getColor(), bClone.getColor());
          assertNotSame(b, bClone);
        });
  }
}
