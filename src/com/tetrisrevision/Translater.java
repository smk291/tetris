package com.tetrisrevision;

import java.util.ArrayList;

abstract class Translater {
  static boolean translate(ArrayList<Cell> cells, Blocks2d field, int y) {
    cells.forEach(p -> p.translate(0, y));

    if (cells.stream().allMatch(p -> PlacementTester.cellCanBeOccupied(p, field))) return true;

    cells.forEach(cell -> cell.translate(0, -y));

    return false;
  }

  static boolean translate(TetrisPiece piece, Blocks2d field, int x, int y, boolean test) {
    piece.getCenter().translate(x, y);

    if (PlacementTester.cellsCanBeOccupied(piece, field)) {
      if (test) piece.getCenter().translate(-x, -y);
      if (piece.getTetromino().isTPiece()) piece.gettSpinTracker().reset();

      return true;
    }

    piece.getCenter().translate(-x, -y);

    return false;
  }

  static int hardDrop(TetrisPiece piece, Blocks2d field) {
    int cells = 0;

    while (true) {
      cells++;

      if (!translate(piece, field, 0, 1, false)) break;
    }

    if (cells > 0 && piece.getTetromino().isTPiece()) piece.gettSpinTracker().reset();

    return cells;
  }
}
