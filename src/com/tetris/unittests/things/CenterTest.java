package com.tetris.unittests.things;

import com.tetris.things.Center;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CenterTest {
  private int x = 5, y = 10, dx = 2, dy = 4;
  private Center c;

  CenterTest() {
    c = new Center(x, y);
  }

  @Test
  void getX() {
    assertEquals(x, c.getX());
  }

  @Test
  void getY() {
    assertEquals(y, c.getY());
  }

  @Test
  void translate() {
    c.translate(dx, dy);
    assertAll(
        () -> {
          assertEquals(x + dx, c.getX());
          assertEquals(y + dy, c.getY());
        });
  }
}
