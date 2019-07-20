package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

abstract public class AddAndRemove {
  static private void apply(Collection<Point> piece, PlayField field, Consumer<Point> consumer) {
    piece.stream()
        .filter(pt -> Position.canBeFilled(pt, field))
        .forEach(consumer);
  }

  static private void apply(Point[] piece, PlayField field, Consumer<Point> consumer) {
    Arrays.stream(piece)
        .filter(pt -> Position.canBeFilled(pt, field))
        .forEach(consumer);
  }

  static public void addFallingPiece(TetrisPiece piece, PlayField field) {
    apply(piece.getPieceLocation(), field, field::fillCell);
  }

  static public void removeFallingPiece(TetrisPiece piece, PlayField field) {
    Arrays.stream(piece.getPieceLocation())
        .filter(Position::isInBounds)
        .forEach(field::emptyCell);
  }

  static public void addAllSinkingPieces(ArrayList<ArrayList<Point>> sinkingPieces, PlayField field) {
    sinkingPieces.forEach(
        piece -> apply(
            piece,
            field,
            field::fillCell
        )
    );
  }

  static public void removeSinkingPieces(ArrayList<ArrayList<Point>> sinkingPieces, PlayField field) {
    sinkingPieces.forEach(
        piece -> piece.stream()
            .filter(Position::isInBounds)
            .forEach(field::emptyCell));
  }

  static void addSinkingPiece(ArrayList<Point> piece, PlayField field) {
    apply(
        piece,
        field,
        field::fillCell
    );
  }
}
