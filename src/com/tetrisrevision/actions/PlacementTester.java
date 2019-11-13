package com.tetrisrevision.actions;

import com.tetrisrevision.things.Square;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.ActiveBlock;

/*
 * These methods first determine whether set of coordinates is in bounds and then, if they are in bounds, whether
 * they represent an empty cell. This is used when rotating, moving, and inserting squares into the playfield.
 */

public abstract class PlacementTester {
  public static boolean cellsCanBeOccupied(ActiveBlock block, RowList field) {
    for (Row r : block.getSquares().get()) {
      for (Square b : r.get()) {
        if (cellCannotBeOccupied(r.getY(), b.getX(), field)) {
          return false;
        }
      }
    }

    return true;
  }

  public static boolean inBounds(int x, int y) {
    return BoundsTester.xInBounds(x) && BoundsTester.yInBounds(y);
  }

  public static boolean cellCannotBeOccupied(int y, int x, RowList f) {
    return !BoundsTester.xInBounds(x) || !BoundsTester.yInLowerBound(y) || f.cellIsNotEmpty(x, y);
  }
}
