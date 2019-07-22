package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

abstract public class AddAndRemove {
  static private void apply(Collection<Point> piece, PlayField field, Consumer<Point> consumer) {
    piece.stream()
        .filter(pt -> CellTester.emptyAndInBoundsAndNoOverlap(pt, field))
        .forEach(consumer);
  }

  static private void apply(Point[] piece, PlayField field, Consumer<Point> consumer) {
    Arrays.stream(piece)
        .filter(pt -> CellTester.emptyAndInBoundsAndNoOverlap(pt, field))
        .forEach(consumer);
  }

  static private void apply(TetrisPiece piece, HashMap<Integer, ArrayList<Cell>> cells) {

  }

  static public void add(TetrisPiece piece, PlayField field) {
    apply(piece.getPoints(), field, field::fillCell);
  }

  static void add(ArrayList<Point> piece, PlayField field) {
    apply(piece, field, field::fillCell);
  }

  static public void remove(TetrisPiece piece, PlayField field) {
    Arrays.stream(piece.getPoints())
        .filter(CellTester::inBounds)
        .forEach(field::emptyCell);
  }

  static public void addFromList(SinkingPieces sinkingPieces, PlayField field) {
    sinkingPieces.getSinkingPieces().forEach(
        piece -> apply(piece, field, field::fillCell)
    );
  }

  static public void removeFromList(SinkingPieces sinkingPieces, PlayField field) {
    sinkingPieces.getSinkingPieces().forEach(
        piece -> piece.stream()
            .filter(CellTester::inBounds)
            .forEach(field::emptyCell));
  }
}

abstract class AddAndRemove2d {
  static private void apply(Collection<Cell> piece, Blocks2d blocks2d, Consumer<Cell> consumer) {
    piece.stream()
        .filter(pt -> CellTester2d.emptyAndInBoundsAndNoOverlap(pt, blocks2d))
        .forEach(consumer);
  }

  static private void apply(Cell[] piece, Blocks2d blocks2d, Consumer<Cell> consumer) {
    Arrays.stream(piece)
        .filter(pt -> CellTester2d.emptyAndInBoundsAndNoOverlap(pt, blocks2d))
        .forEach(consumer);
  }

  static public void add(TetrisPiece piece, Blocks2d blocks2d) {
    apply(piece.getCells(), blocks2d, blocks2d::addCell);
  }

  static void add(ArrayList<Cell> piece, Blocks2d blocks2d) {
    apply(piece, blocks2d, blocks2d::addCell);
  }

  static public void remove(TetrisPiece piece, Blocks2d blocks2d) {
    Arrays.stream(piece.getCells())
        .filter(CellTester2d::inBounds)
        .forEach(blocks2d::deleteCell);
  }

  static public void addFromList(SinkingPieces2d sinkingPieces, Blocks2d blocks2d) {
    sinkingPieces.getPieces().forEach(
        piece -> apply(piece, blocks2d, blocks2d::addCell)
    );
  }

  static public void removeFromList(SinkingPieces2d sinkingPieces, Blocks2d blocks2d) {
    sinkingPieces.getPieces().forEach(
        piece -> piece.stream()
            .filter(CellTester2d::inBounds)
            .forEach(blocks2d::deleteCell));
  }
}
