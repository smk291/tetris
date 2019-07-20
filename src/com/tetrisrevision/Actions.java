package com.tetrisrevision;

/**
 * **
 *
 * <p>Actions class contains all logic for moving and rotating pieces. sinkingPieces never
 * rotate. They only drop. the falling piece can rotate, hardDrop or translate (move in any
 * direction).
 *
 * <p>The basic logic is: - mutates the position of the piece either by translating x/y or rotating
 * - test the resulting position for validity - undo the rotation or translation if the resulting
 * position is invalid
 *
 * <p>**
 */

abstract class Rotator {
  static void tryRotate(int incr, TetrisPiece piece, PlayField field) {
    int oldPrevOrientation = piece.getPrevRotation();
    int oldOrientation = piece.getRotation();

    piece.incrOrientation(incr);
    piece.setPrevRotation(oldOrientation);

    if (piece.getRotation() < 0) {
      piece.setRotation(piece.getRotationMax() - 1);
    } else if (piece.getRotation() >= piece.getRotationMax()) {
      piece.setRotation(0);
    }

    if (!Position.isInBoundsAndEmpty(piece, field)) {
      if (Kicker.tryKick(piece, field)) return;

      piece.setRotation(oldOrientation);
      piece.setPrevRotation(oldPrevOrientation);
    }
  }
}

abstract class Kicker {
  static boolean tryKick(TetrisPiece piece, PlayField field) {
    Integer[][] kickOffsets =
        piece.getKickData().get(piece.getPrevRotation()).get(piece.getRotation());

    for (Integer[] offset : kickOffsets) {
      piece.getCenter().translate(offset[0], offset[1]);

      if (Position.isInBoundsAndEmpty(piece, field)) {
        return true;
      }

      piece.getCenter().translate(-offset[0], -offset[1]);
    }

    return false;
  }
}
