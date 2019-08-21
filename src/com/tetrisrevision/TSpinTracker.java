package com.tetrisrevision;

import java.awt.*;

class TSpinTracker {
  private boolean lastActionIsRotation;
  private boolean lastActionIsKick;
  private Integer lastKick;

  TSpinTracker() {
    reset();
  }

  private static boolean areThreeOrMoreCornersFilled(TetrisPiece piece, RowList rowList) {
    int cornersFilled = 0;
    Point center = piece.getCenter();

    int x = (int) center.getX();
    int y = (int) center.getY();

    cornersFilled += cornerFilled(rowList, x, y, -1, -1);
    cornersFilled += cornerFilled(rowList, x, y, -1, 1);
    cornersFilled += cornerFilled(rowList, x, y, 1, -1);
    cornersFilled += cornerFilled(rowList, x, y, 1, 1);

    return cornersFilled >= 3;
  }

  private static int cornerFilled(RowList rowList, int x, int y, int x2, int y2) {
    return !rowList.isCellEmpty(x + x2, y + y2) ? 1 : 0;
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

  boolean isTSpin(TetrisPiece piece, RowList rowList) {
    return areThreeOrMoreCornersFilled(piece, rowList) || (null != lastKick && lastKick == 4)
        || (!areThreeOrMoreCornersFilled(piece, rowList) && lastKick != null && lastKick == 4);
  }

  boolean isTSpinMini(TetrisPiece piece, RowList rowList) {
    return areThreeOrMoreCornersFilled(piece, rowList) && (lastKick == null || lastKick != 4);
  }
}
