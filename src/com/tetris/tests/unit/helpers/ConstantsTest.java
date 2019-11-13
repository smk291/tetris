package com.tetris.tests.unit.helpers;

import com.tetris.game.constants.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantsTest {
  @Test
  void constants() {
    assertEquals(-1, Constants.down);
    assertEquals(1, Constants.up);
    assertEquals(-1, Constants.left);
    assertEquals(1, Constants.right);

//    assertEquals(10, Constants.width);
    assertEquals(20, Constants.height);

    assertEquals(Constants.height - 1, Constants.topRow);
    assertEquals(0, Constants.bottomRow);
    assertEquals(0, Constants.leftBound);
    assertEquals(9, Constants.rightBound);

    assertEquals(1, Constants.clockwise);
    assertEquals(-1, Constants.counterClockwise);
  }

  @Test
  void movementCalculation() {
    assertEquals(-6, Constants.fromLeft(-5));
    assertEquals(5, Constants.fromRight(4));
    assertEquals(10, Constants.fromBottom(10));
    assertEquals(9, Constants.fromTop(10));
  }
}
