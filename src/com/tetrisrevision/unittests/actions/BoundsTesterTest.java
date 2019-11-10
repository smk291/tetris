package com.tetrisrevision.unittests.actions;

import com.tetrisrevision.actions.BoundsTester;

import static org.junit.jupiter.api.Assertions.*;

class BoundsTesterTest {
  private Boolean xInB(int x) {
    return BoundsTester.xInBounds(x);
  }

  private Boolean yInBNUL(int y) {
    return BoundsTester.yInBoundsNoUpperLimit(y);
  }

  private Boolean yInB(int y) {
    return BoundsTester.yInBounds(y);
  }

  @org.junit.jupiter.api.Test
  void xInBounds() {
    assertAll(
        () -> {
          assertTrue(xInB(0));
          assertTrue(xInB(5));
          assertTrue(xInB(9));
          assertFalse(xInB(-1));
          assertFalse(xInB(10));
        });
  }

  @org.junit.jupiter.api.Test
  void yInBoundsNoUpperLimit() {
    assertAll(
        () -> {
          assertTrue(yInBNUL(0));
          assertTrue(yInBNUL(10));
          assertTrue(yInBNUL(19));
          assertFalse(yInBNUL(-1));
          assertTrue(yInBNUL(30));
        });
  }

  @org.junit.jupiter.api.Test
  void yInBounds() {
    assertAll(
        () -> {
          assertTrue(yInB(0));
          assertTrue(yInB(10));
          assertTrue(yInB(19));
          assertFalse(yInB(-1));
          assertFalse(yInB(30));
        });
  }
}
