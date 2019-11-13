package com.tetris.gui;

import com.tetris.RunTetris;
import com.tetris.actions.Translater;
import com.tetris.gui.constants.GUIConstants;
import com.tetris.gui.input.CommandKeyCodes;
import com.tetris.constants.Constants;
import com.tetris.unittests.integration.IntegrationTests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisGUI {
  private boolean shift = false;
  private Timer timer;
  private Timer timer2;
  private RunTetris runTetris;
  private JFrame tetrisFrame;

  public TetrisGUI() {}

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private void createAndShowGUI() {
    runTetris = new RunTetris();
    tetrisFrame = new MainTetrisGUI(runTetris);

    tetrisFrame.pack();
    tetrisFrame.setSize(new Dimension(GUIConstants.frameWidth, GUIConstants.frameHeight));
    tetrisFrame.setVisible(true);
    tetrisFrame.setFocusable(true);

    tetrisFrame.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {}

          @Override
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = true;
            else keyboardInput(e, shift);

            tetrisFrame.repaint();
          }

          @Override
          public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = false;

            tetrisFrame.repaint();
          }
        });

    timer =
        new Timer(
            1000,
            e -> {
              runTetris.dropCurrentPiece(tetrisFrame);

              tetrisFrame.repaint();
            });
    timer.start();

    timer2 =
        new Timer(
            200,
            e -> {
              runTetris.dropSinkingPieces();

              tetrisFrame.repaint();
            });
    timer2.start();
  }

  public void keyboardInput(KeyEvent e, boolean shift) {
    int k = e.getKeyCode();

    if (k == CommandKeyCodes.getSwitchHoldPiece()) {
      runTetris.setHoldPiece();
    } else if (shift) {
      if (k == CommandKeyCodes.getCounterClockwise()) {
        runTetris.rotate(tetrisFrame, Constants.counterClockwise);
      } else if (k == CommandKeyCodes.getClockwise()) {
        runTetris.rotate(tetrisFrame, Constants.clockwise);
      } else if (k == CommandKeyCodes.getHardDrop()) {
        while (!runTetris.getSinkingPieces().isEmpty()) runTetris.dropSinkingPieces();
        int rowsTraversed =
            Translater.hardDrop(runTetris.getCurrentPiece(), runTetris.getPlayfield());
        runTetris.getRecordKeeping().hardDrop(rowsTraversed);
        runTetris.addPieceToPlayfield(runTetris.getCurrentPiece());
      } else {
        IntegrationTests.accept(
            Character.toString(e.getKeyChar()),
            runTetris.getCurrentPiece(),
            runTetris.getPlayfield());
      }
    } else {
      if (k == CommandKeyCodes.getLeft()) {
        runTetris.translatePiece(tetrisFrame, Constants.left, 0);
      } else if (k == CommandKeyCodes.getRight()) {
        runTetris.translatePiece(tetrisFrame, Constants.right, 0);
      } else if (k == CommandKeyCodes.getDrop()) {
        runTetris.translatePiece(tetrisFrame, 0, Constants.down);
        runTetris.getRecordKeeping().softDrop();
      } else if (k == CommandKeyCodes.getUp()) {
        runTetris.translatePiece(tetrisFrame, 0, Constants.up);
      } else {
        IntegrationTests.accept(
            Character.toString(e.getKeyChar()),
            runTetris.getCurrentPiece(),
            runTetris.getPlayfield());
      }
    }
  }

  public void init() {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    } catch (UnsupportedLookAndFeelException
        | IllegalAccessException
        | InstantiationException
        | ClassNotFoundException ex) {
      ex.printStackTrace();
    }

    UIManager.put("swing.boldMetal", Boolean.FALSE);
    SwingUtilities.invokeLater(this::createAndShowGUI);
  }

  void setDropTimerDelay(int ms) {
    timer.setDelay(ms);
  }

  public void endGame() {
    timer.stop();
    timer2.stop();
  }
}
