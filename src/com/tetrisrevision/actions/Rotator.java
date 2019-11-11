package com.tetrisrevision.actions;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;

/**
 * Actions class contains all logic for moving and rotating pieces. sinkingPieces never rotate. They
 * only drop. the falling piece can rotate, hardDrop or translate (move in any direction).
 *
 * <p>The basic logic is: - mutates the position of the piece either by translating x/y or rotating
 * - test the resulting position for validity - undo the rotation or translation if the resulting
 * position is invalid
 */
public abstract class Rotator {
  public static int apply(int incr, TetrisPiece piece, RowList rowList) {
    int rotation = piece.getRotation();
    int prevRotation = piece.getPrevRotation();

    piece.incrementRotation(incr);

    if (!PlacementTester.cellsCanBeOccupied(piece, rowList)) {
      int kickValue = tryKick(piece, rowList);
      if (kickValue > -1) {
        return kickValue;
      }

      if (tryLift(piece, rowList)) {
        return Constants.width + 1;
      }

      piece.setRotation(rotation);
      piece.setPrevRotation(prevRotation);

      return -1;
    }

    if (piece.getTetromino().isTPiece()) {
      setTSpinData(piece, null, false);
    }

    return Constants.width + 1;
  }

  private static Integer tryKick(TetrisPiece piece, RowList rowList) {
    Integer kickIdx = WallKicker.tryKick(piece, rowList);

    if (null == kickIdx) {
      return -1;
    }

    if (piece.getTetromino().isTPiece()) {
      setTSpinData(piece, kickIdx, true);
    }

    return kickIdx;
  }

  private static boolean tryLift(TetrisPiece piece, RowList rowList) {
    boolean canDrop = Translater.translate(piece, rowList, 0, Constants.down, true);

    if (!canDrop && Translater.translate(piece, rowList, 0, Constants.up, false)) {
      if (piece.getTetromino().isTPiece()) {
        setTSpinData(piece, null, false);
      }

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
