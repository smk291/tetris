package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public class AddAndRemove {
  private PlayField playField;
  private TetrisPiece falling;
  private ArrayList<ArrayList<Point>> sinkingPieces;
  private Position position;

  AddAndRemove(
      PlayField playField,
      TetrisPiece falling,
      ArrayList<ArrayList<Point>> sinkingPieces
  ) {
    this.playField = playField;
    this.falling = falling;
    this.sinkingPieces = sinkingPieces;
    Bounds bounds = new Bounds();
    Overlap overlap = new Overlap(bounds, playField);
    this.position = new Position(falling, bounds, overlap);
  }

  private void apply(Collection<Point> piece, Consumer<Point> consumer) {
    piece.stream().filter(position::canBeFilled).forEach(consumer);
  }

  private void apply(Point[] piece, Consumer<Point> consumer) {
    Arrays.stream(piece).filter(position::canBeFilled).forEach(consumer);
  }

  public void addFallingPiece() {
    apply(falling.getPieceLocation(), playField::fillCell);
  }

  public void removeFallingPiece() {
    Arrays.stream(falling.getPieceLocation())
        .filter(position::isInBounds)
        .forEach(playField::emptyCell);
  }

  public void addAllSinkingPieces() {
    sinkingPieces.forEach(piece -> apply(piece, playField::fillCell));
  }

  public void removeSinkingPieces() {
    sinkingPieces.forEach(
        piece -> piece.stream().filter(position::isInBounds).forEach(playField::emptyCell));
  }

  void addSinkingPiece(ArrayList<Point> piece) {
    apply(piece, playField::fillCell);
  }
}
