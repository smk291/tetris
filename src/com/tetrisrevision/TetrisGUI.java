package com.tetrisrevision;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class TetrisGUI {
  boolean shift = false;
  private Timer timer;
  private Timer timer2;
//  private JFrame frame = new JFrame("Tetris");
  private RunTetris runTetris;
  private PlayFieldGUI bc;
  private JFrame tetrisFrame;

  TetrisGUI() {
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private void createAndShowGUI() {
    runTetris = new RunTetris();
    runTetris.setTetrisGUI(this);
    bc = new PlayFieldGUI(runTetris);
    tetrisFrame = new MainTetris(runTetris);

    tetrisFrame.pack();
    tetrisFrame.setSize(new Dimension(600, 400));
    tetrisFrame.setVisible(true);
    tetrisFrame.setFocusable(true);

    tetrisFrame.addKeyListener(
            new KeyListener() {
              @Override
              public void keyTyped(KeyEvent e) {}

              @Override
              public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = true;
                else runTetris.keyboardInput(e, shift);

                tetrisFrame.repaint();
              }

              @Override
              public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = false;

                tetrisFrame.repaint();
              }
            });

    timer = new Timer(1000, e -> {
      runTetris.dropCurrentPiece();
      tetrisFrame.repaint();
    });
    timer.start();

    timer2 = new Timer(200, e -> {
      runTetris.dropSinkingPieces();

      tetrisFrame.repaint();
    });
    timer2.start();
  }

  void init() {
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

  void endGame() {
    timer.stop();
    timer2.stop();
  }

  PlayFieldGUI getBoardCompositor() {
    return bc;
  }
}

