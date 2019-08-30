package com.tetrisrevision.actions;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;

public abstract class Translater {
  public static boolean translate(RowList blocks, RowList field, double y) {
    blocks.forEach(r -> r.setY(r.getY() + y));

    boolean validPosition = true;

    for (Row r : blocks.get()) {
      for (Block b : r.get()) {
        if (!PlacementTester.cellCanBeOccupied(r.getY(), b.getX(), field)) {
          validPosition = false;
        }
      }
    }

    if (!validPosition)
      blocks.forEach(r -> r.setY(r.getY() - y));

    return validPosition;
  }

  public static boolean translate(TetrisPiece piece, RowList field, int x, int y, boolean test) {
    piece.getCenter().translate(x, y);

    boolean validPosition = PlacementTester.cellsCanBeOccupied(piece, field);

    if (validPosition) {
      if (test) {
        piece.getCenter().translate(-x, -y);
      }

      if (piece.getTetromino().isTPiece()) {
        piece.gettSpinTracker().reset();
      }

      return true;
    }

    piece.getCenter().translate(-x, -y);

    return false;
  }

  public static int hardDrop(TetrisPiece piece, RowList field) {
    int cells = 0;

    do {
      cells++;

    } while (translate(piece, field, 0, Constants.down, false));

    if (cells > 0 && piece.getTetromino().isTPiece()) {
      piece.gettSpinTracker().reset();
    }

    return cells;
  }
}
