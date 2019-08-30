package com.tetrisrevision.actions;

import com.tetrisrevision.actions.BoundsTester;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;

public abstract class PlacementTester {
  public static boolean cellsCanBeOccupied(TetrisPiece piece, RowList field) {
    for (Row r : piece.getBlocks().get()) {
      for (Block b : r.get()) {
        if (!cellCanBeOccupied(r.getY(), b.getX(), field)) {
          return false;
        }
      }
    }

    return true;
  }

  static boolean inBounds(double x, double y) {
    return BoundsTester.xInBounds(x) && BoundsTester.yInBounds(y);
  }

  public static boolean cellCanBeOccupied(double y, double x, RowList f) {
    return BoundsTester.xInBounds(x)
        && BoundsTester.yInBoundsNoMin(y)
        && f.cellIsEmpty(x, y);
  }
}
