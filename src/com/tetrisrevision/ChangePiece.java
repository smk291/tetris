package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

/**
 * **
 *
 * <p>ChangePiece class contains all logic for moving and rotating pieces. sinkingPieces never
 * rotate. They only drop. the falling piece can rotate, hardDrop or translate (move in any
 * direction).
 *
 * <p>The basic logic is: - mutates the position of the piece either by translating x/y or rotating
 * - test the resulting position for validity - undo the rotation or translation if the resulting
 * position is invalid
 *
 * <p>**
 */
class ChangePiece {
  private TetrisPiece falling;
  private ArrayList<ArrayList<Point>> sinkingPieces;
  private Test test;
  PositionChange position;
  Rotation rotation;
  Kick kick;

  ChangePiece(TetrisPiece falling, ArrayList<ArrayList<Point>> sinkingPieces, Test test) {
    this.falling = falling;
    this.sinkingPieces = sinkingPieces;
    this.position = new PositionChange(test, sinkingPieces, falling);
    this.kick = new Kick(falling, test);
    this.rotation = new Rotation(falling, test, kick);
  }
}

class PositionChange {
  Test test;
  ArrayList<ArrayList<Point>> sinkingPieces;
  TetrisPiece falling;

  PositionChange(Test test, ArrayList<ArrayList<Point>> sinkingPieces, TetrisPiece falling) {
    this.test = test;
    this.sinkingPieces = sinkingPieces;
    this.falling = falling;
  }

  void tryRaiseSinkingPiece(ArrayList<Point> s) {
    s.forEach(pt -> pt.translate(0, -1));

    if (s.stream().allMatch(test.position::pointIsValidNoMin)) {
      return;
    }

    s.forEach(p -> p.translate(0, 1));
  }

  boolean trySoftDropSinkingPiece(ArrayList<Point> s) {
    s.forEach(pt -> pt.translate(0, 1));

    if (s.stream().allMatch(test.position::pointIsValidNoMin)) {
      return true;
    }

    s.forEach(p -> p.translate(0, -1));

    return false;
  }

  void trySoftDropSinkingPieces() {
    sinkingPieces.forEach(this::trySoftDropSinkingPiece);
  }

  void hardDrop() {
    falling.setAddToBoard(true);

    while (true) {
      if (!tryTranslateFallingPiece(0, 1)) break;
    }
  }

  boolean tryTranslateFallingPiece(int x, int y) {
    falling.getCenter().translate(x, y);

    if (test.position.isInBoundsAndEmptyNoRowMin()) {
      return true;
    }

    falling.getCenter().translate(-x, -y);

    return false;
  }
}

class Rotation {
  TetrisPiece falling;
  Test test;
  Kick kick;

  Rotation(TetrisPiece falling, Test test, Kick kick) {
    this.falling = falling;
    this.test = test;
    this.kick = kick;
  }

  void tryRotate(int incr) {
    int oldPrevOrientation = falling.getPrevRotation();
    int oldOrientation = falling.getRotation();

    falling.incrOrientation(incr);
    falling.setPrevRotation(oldOrientation);

    if (falling.getRotation() < 0) {
      falling.setRotation(falling.getRotationMax() - 1);
    } else if (falling.getRotation() >= falling.getRotationMax()) {
      falling.setRotation(0);
    }

    if (!test.position.isInBoundsAndEmpty()) {
      if (kick.tryKick()) return;

      falling.setRotation(oldOrientation);
      falling.setPrevRotation(oldPrevOrientation);
    }
  }
}

class Kick {
  TetrisPiece falling;
  Test test;

  Kick(TetrisPiece falling, Test test) {
    this.falling = falling;
    this.test = test;
  }

  boolean tryKick() {
    Integer[][] kickOffsets =
        falling.getKickData().get(falling.getPrevRotation()).get(falling.getRotation());

    for (Integer[] offset : kickOffsets) {
      falling.getCenter().translate(offset[0], offset[1]);

      if (test.position.isInBoundsAndEmpty()) {
        return true;
      }

      falling.getCenter().translate(-offset[0], -offset[1]);
    }

    return false;
  }
}
