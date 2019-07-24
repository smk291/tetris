package com.tetrisrevision;

/**
 * **
 *
 * <p>Actions class contains all logic for moving and rotating pieces. sinkingPieces never rotate.
 * They only drop. the falling piece can rotate, hardDrop or translate (move in any direction).
 *
 * <p>The basic logic is: - mutates the position of the piece either by translating x/y or rotating
 * - test the resulting position for validity - undo the rotation or translation if the resulting
 * position is invalid
 *
 * <p>**
 */
abstract class Rotator {
  static boolean apply(int incr, TetrisPiece piece, Blocks2d blocks2d) {
    int oldPrevOrientation = piece.getPrevRotation();
    int oldOrientation = piece.getRotation();

    piece.incrementRotation(incr);
    piece.setPrevRotation(oldOrientation);

    if (piece.getRotation() < 0) {
      piece.setRotation(piece.getRotationMax() - 1);
    } else if (piece.getRotation() >= piece.getRotationMax()) {
      piece.setRotation(0);
    }

    if (!CellTester.emptyAndInBoundsAndNoOverlapNoMin(piece, blocks2d)) {
      if (piece.pieceIsAtEdge() && WallKicker.tryKick(piece, blocks2d)) return true;

      boolean canDrop = Translater.translate(piece, blocks2d, 0, 1, true);

      if (!canDrop && Translater.translate(piece, blocks2d, 0, -1, false)) return true;

      piece.setRotation(oldOrientation);
      piece.setPrevRotation(oldPrevOrientation);

      return false;
    }

    return true;
  }
}
