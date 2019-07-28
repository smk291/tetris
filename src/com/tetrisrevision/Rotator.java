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
  static boolean apply(int incr, TetrisPiece piece, PlayField playField) {
    piece.incrementRotation(incr);

    if (!PlacementTester.cellsCanBeOccupied(piece, playField)) {
      if (tryKick(piece, playField) || tryLift(piece, playField)) return true;

      piece.incrementRotation(-incr);

      return false;
    }

    if (piece.getTetromino().isTPiece()) setTSpinData(piece, null, false);

    return true;
  }

  private static boolean tryKick(TetrisPiece piece, PlayField playField) {
    boolean canTranslateLeft = Translater.translate(piece, playField, -1, 0, true);
    boolean canTranslateRight = Translater.translate(piece, playField, 1, 0, true);

    if (canTranslateLeft && canTranslateRight) return false;

    Integer kickIdx = WallKicker.tryKick(piece, playField);

    if (null != kickIdx) {
      if (piece.getTetromino().isTPiece()) setTSpinData(piece, kickIdx, true);

      return true;
    }

    return false;
  }

  private static boolean tryLift(TetrisPiece piece, PlayField playField) {
    boolean canDrop = Translater.translate(piece, playField, 0, 1, true);

    if (!canDrop && Translater.translate(piece, playField, 0, -1, false)) {
      if (piece.getTetromino().isTPiece()) setTSpinData(piece, null, false);

      return true;
    }

    return false;
  }

  private static void setTSpinData(TetrisPiece piece, Integer kickIdx, boolean lastActionIsKick) {
    piece.gettSpinTracker().setLastKick(kickIdx);
    piece.gettSpinTracker().setLastActionIsRotation(true);
    piece.gettSpinTracker().setLastActionIsKick(lastActionIsKick);
  }
}
