package com.tetrisrevision;

abstract class Translater {
  static boolean translate(RowList blocks, RowList field, double y) {
    blocks.forEach(r -> r.setY(r.getY() + y));

    boolean validPosition = true;

    for (Row r : blocks.get()) {
      if (!r.allMatch(b -> PlacementTester.cellCanBeOccupied(r.getY(), b.getX(), field))) {
        validPosition = false;

        break;
      }
    }

    if (!validPosition) blocks.forEach(r -> r.setY(r.getY() - y));

    return validPosition;
  }

  static boolean translate(TetrisPiece piece, RowList field, int x, int y, boolean test) {
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

  static int hardDrop(TetrisPiece piece, RowList field) {
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
