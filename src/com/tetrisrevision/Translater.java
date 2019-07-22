package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

abstract class Translater {
  static boolean translate(ArrayList<Point> s, PlayField field, int x, int y) {
    s.forEach(pt -> pt.translate(x, y));

    if (s.stream().allMatch(pt -> CellTester.emptyAndInBoundsAndNoOverlapNoMin(pt, field))) {
      return true;
    }

    s.forEach(p -> p.translate(-x, -y));

    return false;
  }

  static boolean translate(TetrisPiece piece, PlayField field, int x, int y) {
    piece.getCenter().translate(x, y);

    if (CellTester.emptyAndInBoundsAndNoOverlapNoMin(piece, field)) {
      return true;
    }

    piece.getCenter().translate(-x, -y);

    return false;
  }

  static void hardDrop(TetrisPiece piece, PlayField field) {
    piece.setAddToBoard(true);

    while (true) {
      if (!translate(piece, field, 0, 1)) break;
    }
  }
}

abstract class Translater2d {
  static boolean translate(ArrayList<Cell> piece, Blocks2d blocks, int x, int y) {
    piece.forEach(c -> c.move(x, y));

    if (piece.stream().allMatch(c -> CellTester2d.emptyAndInBoundsAndNoOverlap(c, blocks))) {
      return true;
    }

    piece.forEach(c -> c.move(-x, -y));

    return false;
  }


  static boolean translate(TetrisPiece piece, Blocks2d blocks, int x, int y) {
    piece.getCenter().translate(x, y);

    if (CellTester2d.emptyAndInBoundsAndNoOverlapNoMin(piece, blocks)) {
      return true;
    }

    piece.getCenter().translate(-x, -y);

    return false;
  }

  static void hardDrop(TetrisPiece piece, Blocks2d blocks2d) {
    piece.setAddToBoard(true);

    while (true) {
      if (!translate(piece, blocks2d, 0, 1)) break;
    }
  }
}
