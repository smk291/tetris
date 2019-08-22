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
  static boolean apply(int incr, TetrisPiece piece, RowList rowList) {
    piece.incrementRotation(incr);

    if (!PlacementTester.cellsCanBeOccupied(piece, rowList)) {
      if (tryKick(piece, rowList) || tryLift(piece, rowList)) return true;

      piece.incrementRotation(-incr);

      return false;
    }

    if (piece.getTetromino().isTPiece()) setTSpinData(piece, null, false);

    return true;
  }

  private static boolean tryKick(TetrisPiece piece, RowList rowList) {
    boolean canTranslateLeft = Translater.translate(piece, rowList, Constants.left(), 0, true);
    boolean canTranslateRight = Translater.translate(piece, rowList, Constants.right(), 0, true);

    if (canTranslateLeft && canTranslateRight) return false;

    Integer kickIdx = WallKicker.tryKick(piece, rowList);

    if (null != kickIdx) {
      if (piece.getTetromino().isTPiece()) setTSpinData(piece, kickIdx, true);

      return true;
    }

    return false;
  }

  private static boolean tryLift(TetrisPiece piece, RowList rowList) {
    boolean canDrop = Translater.translate(piece, rowList, 0, Constants.down(), true);

    if (!canDrop && Translater.translate(piece, rowList, 0, Constants.up(), false)) {
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
