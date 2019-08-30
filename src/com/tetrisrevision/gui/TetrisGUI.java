package com.tetrisrevision.gui;

import com.tetrisrevision.RunTetris;

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

  public TetrisGUI() {
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private void createAndShowGUI() {
    runTetris = new RunTetris();
    bc = new PlayField(runTetris);
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

