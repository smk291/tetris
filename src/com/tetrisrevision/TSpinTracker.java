package com.tetrisrevision;

import java.awt.*;

class TSpinTracker {
  private boolean lastActionIsRotation;
  private boolean lastActionIsKick;
  private Integer lastKick;

  TSpinTracker() {
    reset();
  }

  void reset() {
    this.lastActionIsRotation = false;
    this.lastActionIsKick = false;
    this.lastKick = null;
  }

  void setLastActionIsRotation(boolean b) {
    this.lastActionIsRotation = b;
  }

  void setLastActionIsKick(boolean b) {
    this.lastActionIsKick = b;
  }

  void setLastKick(Integer nullableInteger) {
    this.lastKick = nullableInteger;
  }

  boolean isTSpin(TetrisPiece piece, Blocks2d blocks2d) {
    return areThreeOrMoreCornersFilled(piece, blocks2d) || (null != lastKick && lastKick == 4);
  }

  boolean isTSpinMini(TetrisPiece piece, Blocks2d blocks2d) {
    return (areThreeOrMoreCornersFilled(piece, blocks2d) && (lastKick == null || lastKick != 4)) ||
        (!areThreeOrMoreCornersFilled(piece, blocks2d) && (lastKick != null && lastKick == 4));
  }

  private static boolean areThreeOrMoreCornersFilled(TetrisPiece piece, Blocks2d blocks2d) {
    int cornersFilled = 0;
    Point center = piece.getCenter();
    int x = (int) center.getX();
    int y = (int) center.getY();

    cornersFilled += cornerFilled(blocks2d, x, y, -1, -1);
    cornersFilled += cornerFilled(blocks2d, x, y, -1, 1);
    cornersFilled += cornerFilled(blocks2d, x, y, 1, -1);
    cornersFilled += cornerFilled(blocks2d, x, y, 1, 1);

    return cornersFilled >= 3;
  }

  private static int cornerFilled(Blocks2d blocks2d, int x, int y, int x2, int y2) {
    return blocks2d.cellIsFull(x + x2, y + y2) ? 1 : 0;
  }
}
