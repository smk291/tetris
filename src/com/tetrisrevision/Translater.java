package com.tetrisrevision;

import java.util.ArrayList;

abstract class Translater {
  static boolean translate(ArrayList<Block> blocks, PlayField field, int y) {
    blocks.forEach(p -> p.translate(0, y));

    if (blocks.stream().allMatch(p -> PlacementTester.cellCanBeOccupied(p, field))) return true;

    blocks.forEach(cell -> cell.translate(0, -y));

    return false;
  }

  static boolean translate(TetrisPiece piece, PlayField field, int x, int y, boolean test) {
    piece.getCenter().translate(x, y);

    if (PlacementTester.cellsCanBeOccupied(piece, field)) {
      if (test) piece.getCenter().translate(-x, -y);
      if (piece.getTetromino().isTPiece()) piece.gettSpinTracker().reset();

      return true;
    }

    piece.getCenter().translate(-x, -y);

    return false;
  }

  static int hardDrop(TetrisPiece piece, PlayField field) {
    int cells = 0;

    while (true) {
      cells++;

      if (!translate(piece, field, 0, 1, false)) break;
    }

    if (cells > 0 && piece.getTetromino().isTPiece()) piece.gettSpinTracker().reset();

    return cells;
  }
}
