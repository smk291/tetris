package com.tetrisrevision.gui;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.actions.Translater;
import com.tetrisrevision.helpers.CommandKeyCodes;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.testing.InputTests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisGUI {
  boolean shift = false;
  private Timer timer;
  private Timer timer2;
  private RunTetris runTetris;
  private PlayField bc;
  private JFrame tetrisFrame;

  public TetrisGUI() {}

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private void createAndShowGUI() {
    runTetris = new RunTetris();
    bc = new PlayField(runTetris);
    tetrisFrame = new MainTetris(runTetris);

    tetrisFrame.pack();
    tetrisFrame.setSize(new Dimension(600, 450));
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
              runTetris.dropCurrentPiece();

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
        runTetris.rotate(Constants.counterClockwise);
      } else if (k == CommandKeyCodes.getClockwise()) {
        runTetris.rotate(Constants.clockwise);
      } else if (k == CommandKeyCodes.getHardDrop()) {
        while (!runTetris.getSinkingPieces().isEmpty()) runTetris.dropSinkingPieces();
        int rowsTraversed =
            Translater.hardDrop(runTetris.getCurrentPiece(), runTetris.getPlayField());
        runTetris.getRecordKeeping().hardDrop(rowsTraversed);
        runTetris.addPieceToBoard(runTetris.getCurrentPiece());
      } else {
        InputTests.accept(
            Character.toString(e.getKeyChar()),
            runTetris.getCurrentPiece(),
            runTetris.getPlayField());
      }
    } else {
      if (k == CommandKeyCodes.getLeft()) {
        runTetris.translatePiece(Constants.left, 0);
      } else if (k == CommandKeyCodes.getRight()) {
        runTetris.translatePiece(Constants.right, 0);
      } else if (k == CommandKeyCodes.getDrop()) {
        runTetris.translatePiece(0, Constants.down);
        runTetris.getRecordKeeping().softDrop();
      } else if (k == CommandKeyCodes.getUp()) {
        runTetris.translatePiece(0, Constants.up);
      } else {
        InputTests.accept(
            Character.toString(e.getKeyChar()),
            runTetris.getCurrentPiece(),
            runTetris.getPlayField());
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
