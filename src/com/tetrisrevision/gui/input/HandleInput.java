package com.tetrisrevision.gui.input;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.actions.Translater;
import com.tetrisrevision.constants.Constants;
import com.tetrisrevision.testing.InputTests;

import javax.swing.*;
import java.awt.event.KeyEvent;

abstract public class HandleInput {
  boolean shift = false;

  public boolean getShift() {
    return shift;
  }

  public static void keyboardInput(RunTetris runTetris, JFrame frame, KeyEvent e, boolean shift) {
    int k = e.getKeyCode();

    if (k == CommandKeyCodes.getSwitchHoldPiece()) {
      runTetris.setHoldPiece();
    } else if (shift) {
      if (k == CommandKeyCodes.getCounterClockwise()) {
        runTetris.rotate(frame, Constants.counterClockwise);
      } else if (k == CommandKeyCodes.getClockwise()) {
        runTetris.rotate(frame, Constants.clockwise);
      } else if (k == CommandKeyCodes.getHardDrop()) {
        while (!runTetris.getSinkingPieces().isEmpty()) runTetris.dropSinkingPieces();
        int rowsTraversed =
            Translater.hardDrop(runTetris.getCurrentPiece(), runTetris.getPlayfield());
        runTetris.getRecordKeeping().hardDrop(rowsTraversed);
        runTetris.addPieceToPlayfield(runTetris.getCurrentPiece());
      } else {
        InputTests.accept(
            Character.toString(e.getKeyChar()),
            runTetris.getCurrentPiece(),
            runTetris.getPlayfield());
      }
    } else {
      if (k == CommandKeyCodes.getLeft()) {
        runTetris.translatePiece(frame, Constants.left, 0);
      } else if (k == CommandKeyCodes.getRight()) {
        runTetris.translatePiece(frame, Constants.right, 0);
      } else if (k == CommandKeyCodes.getDrop()) {
        runTetris.translatePiece(frame, 0, Constants.down);
        runTetris.getRecordKeeping().softDrop();
//      } else if (k == CommandKeyCodes.getUp()) {
//        runTetris.translatePiece(0, Constants.up);
      } else {
        InputTests.accept(
            Character.toString(e.getKeyChar()),
            runTetris.getCurrentPiece(),
            runTetris.getPlayfield());
      }
    }
  }
}
