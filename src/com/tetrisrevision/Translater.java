package com.tetrisrevision;

import java.util.ArrayList;

abstract class Translater {
  static boolean translate(ArrayList<Cell> cells, Blocks2d field, int y) {
    cells.forEach(p -> p.translate(0, y));

    if (cells.stream().allMatch(p -> CellTester.emptyAndInBoundsAndNoOverlapNoMin(p, field))) {
      return true;
    }

    cells.forEach(cell -> cell.translate(0, -y));

    return false;
  }

  static boolean translate(TetrisPiece piece, Blocks2d field, int x, int y, boolean testDrop) {
    piece.getCenter().translate(x, y);

    if (CellTester.emptyAndInBoundsAndNoOverlapNoMin(piece, field)) {
      if (testDrop) piece.getCenter().translate(-x, -y);

      return true;
    }

    piece.getCenter().translate(-x, -y);

    return false;
  }

  static void hardDrop(TetrisPiece piece, Blocks2d field) {
    piece.setAddToBoard(true);

    while (true) {
      if (!translate(piece, field, 0, 1, false)) break;
    }
  }
}
