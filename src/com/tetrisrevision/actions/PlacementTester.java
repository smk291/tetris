package com.tetrisrevision.actions;

import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;

public abstract class PlacementTester {
  public static boolean cellsCanBeOccupied(TetrisPiece piece, RowList field) {
    for (Row r : piece.getBlocks().get()) {
      for (Block b : r.get()) {
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
    return !BoundsTester.xInBounds(x) || !BoundsTester.yInBoundsNoUpperLimit(y) || f.cellIsNotEmpty(x, y);
  }
}
