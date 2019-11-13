package com.tetris.gui.input;

import com.tetris.game.RunTetris;
import com.tetris.game.actions.Translater;
import com.tetris.game.constants.Constants;
import com.tetris.tests.integration.IntegrationTests;

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
        while (!runTetris.getSinkingBlocks().isEmpty()) runTetris.dropSinkingBlocks();
        int rowsTraversed =
            Translater.hardDrop(runTetris.getActiveBlock(), runTetris.getPlayfield());
        runTetris.getRecordKeeping().hardDrop(rowsTraversed);
        runTetris.addBlockToPlayfield(runTetris.getActiveBlock());
      } else {
        IntegrationTests.accept(
            Character.toString(e.getKeyChar()),
            runTetris.getActiveBlock(),
            runTetris.getPlayfield());
      }
    } else {
      if (k == CommandKeyCodes.getLeft()) {
        runTetris.translate(frame, Constants.left, 0);
      } else if (k == CommandKeyCodes.getRight()) {
        runTetris.translate(frame, Constants.right, 0);
      } else if (k == CommandKeyCodes.getDrop()) {
        runTetris.translate(frame, 0, Constants.down);
        runTetris.getRecordKeeping().softDrop();
//      } else if (k == CommandKeyCodes.getUp()) {
//        runTetris.translateBlock(0, Constants.up);
      } else {
        IntegrationTests.accept(
            Character.toString(e.getKeyChar()),
            runTetris.getActiveBlock(),
            runTetris.getPlayfield());
      }
    }
  }
}
