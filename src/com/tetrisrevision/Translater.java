package com.tetrisrevision;

abstract class Translater {
  static boolean translate(RowList blocks, RowList field, double y) {
    blocks.forEach(r -> r.setY(r.getY() + y));

    boolean validPosition = false;

    for (Row r : blocks)
    {
      if (r.allMatch(p -> PlacementTester.cellCanBeOccupied(r.getY(), p, field)))
      {
        validPosition = true;
      }
      else {
        validPosition = false;
        break;
      }
    }

    if (!validPosition)
      blocks.forEach(r -> r.setY(r.getY() - y));

    return false;
  }

  static boolean translate(TetrisPiece piece, RowList field, int x, int y, boolean test) {
    piece.getCenter().translate(x, y);

    if (PlacementTester.cellsCanBeOccupied(piece, field)) {
      if (test) piece.getCenter().translate(-x, -y);
      if (piece.getTetromino().isTPiece()) piece.gettSpinTracker().reset();

      return true;
    }

    piece.getCenter().translate(-x, -y);

    return false;
  }

  static int hardDrop(TetrisPiece piece, RowList field) {
    int cells = 0;

    while (true) {
      cells++;

      if (!translate(piece, field, 0, 1, false)) break;
    }

    if (cells > 0 && piece.getTetromino().isTPiece()) piece.gettSpinTracker().reset();

    return cells;
  }
}
