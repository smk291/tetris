package com.tetrisrevision.unittests.things;

import com.tetrisrevision.things.Block;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
  private int x = 4;
  private Block b = new Block(x, Color.red);

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
    Block bClone = b.clone();

    assertAll(
        () -> {
          assertEquals(b.getX(), bClone.getX());
          assertEquals(b.getColor(), bClone.getColor());
          assertNotSame(b, bClone);
        });
  }
}
