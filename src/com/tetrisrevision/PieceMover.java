package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

abstract class PieceMover {
  static void tryRaiseSinkingPiece(ArrayList<Point> s, PlayField field) {
    s.forEach(pt -> pt.translate(0, -1));

    if (s.stream().allMatch(pt -> Position.pointIsValidNoMin(pt, field))) {
      return;
    }

    s.forEach(p -> p.translate(0, 1));
  }

  static boolean trySoftDropSinkingPiece(ArrayList<Point> s, PlayField field) {
    s.forEach(pt -> pt.translate(0, 1));

    if (s.stream().allMatch(pt -> Position.pointIsValidNoMin(pt, field))) {
      return true;
    }

    s.forEach(p -> p.translate(0, -1));

    return false;
  }

  static void trySoftDropSinkingPieces(ArrayList<ArrayList<Point>> sinkingPieces, PlayField field) {
    sinkingPieces.forEach(sp -> trySoftDropSinkingPiece(sp, field));
  }

  static void hardDrop(TetrisPiece piece, PlayField field) {
    piece.setAddToBoard(true);

    while (true) {
      if (!tryTranslateFallingPiece(piece, field,0, 1)) break;
    }
  }

  static boolean tryTranslateFallingPiece(TetrisPiece piece, PlayField field, int x, int y) {
    piece.getCenter().translate(x, y);

    if (Position.isInBoundsAndEmptyNoRowMin(piece, field)) {
      return true;
    }

    piece.getCenter().translate(-x, -y);

    return false;
  }
}
