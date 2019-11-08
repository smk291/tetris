package com.tetrisrevision.unittests.helpers;

import com.tetrisrevision.helpers.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {
  @Test
  void constants() {
    Assertions.assertEquals(-1, Constants.down);
    assertEquals(1, Constants.up);
    assertEquals(-1, Constants.left);
    assertEquals(1, Constants.right);

    assertEquals(10, Constants.width);
    assertEquals(20, Constants.height);

    assertEquals(Constants.height - 1, Constants.topRow);
    assertEquals(0, Constants.bottomRow);
//    assertEquals(Constants.bottomRow - 1, Constants.bottomBoundOuter);
    assertEquals(0, Constants.leftBound);
//    assertEquals(-1, Constants.leftBoundInner);
    assertEquals(9, Constants.rightBound);
//    assertEquals(Constants.width, Constants.rightBoundOuter);

    assertEquals(1, Constants.clockwise);
    assertEquals(-1, Constants.counterClockwise);
  }
  @Test
  void movementCalculation() {
    assertEquals(-5, Constants.fromLeft(6));
    assertEquals(5, Constants.fromRight(6));
    assertEquals(10, Constants.fromBottom(10));
    assertEquals(9, Constants.fromTop(10));
  }
}