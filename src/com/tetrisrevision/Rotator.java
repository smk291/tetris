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
    piece.incrementRotation(incr);

    if (!PlacementTester.cellsCanBeOccupied(piece, blocks2d)) {
      if (tryKick(piece, blocks2d) || tryLift(piece, blocks2d)) return true;

      piece.incrementRotation(-incr);

      return false;
    }

    if (piece.getTetromino().isTPiece()) setTSpinData(piece, null, false);

    return true;
  }

  private static boolean tryKick(TetrisPiece piece, Blocks2d blocks2d) {
    boolean canTranslateLeft = Translater.translate(piece, blocks2d, -1, 0, true);
    boolean canTranslateRight = Translater.translate(piece, blocks2d, 1, 0, true);

    if (canTranslateLeft && canTranslateRight) return false;

    Integer kickIdx = WallKicker.tryKick(piece, blocks2d);

    if (null != kickIdx) {
      if (piece.getTetromino().isTPiece()) setTSpinData(piece, kickIdx, true);

      return true;
    }

    return false;
  }

  private static boolean tryLift(TetrisPiece piece, Blocks2d blocks2d) {
    boolean canDrop = Translater.translate(piece, blocks2d, 0, 1, true);

    if (!canDrop && Translater.translate(piece, blocks2d, 0, -1, false)) {
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
