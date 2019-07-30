package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

class TetrisGUI {
  private Timer timer;
  private Timer timer2;
  private JFrame frame = new JFrame("Tetris");
  private boolean RIGHT_TO_LEFT = false;
  private RunTetris runTetris;
  PlayFieldGUI bc = new PlayFieldGUI(runTetris);
  boolean shift = false;

  TetrisGUI(RunTetris runTetris) {
    this.runTetris = runTetris;
  }

  private void addComponentsToPane(Container pane) {
    if (!(pane.getLayout() instanceof BorderLayout)) {
      pane.add(new JLabel("Container doesn't use BorderLayout!"));

      return;
    }

    bc = new PlayFieldGUI(runTetris);
    pane.add(bc, BorderLayout.CENTER);
    GameData gd = new GameData(runTetris);
    pane.add(gd, BorderLayout.CENTER);
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private void createAndShowGUI() {
//     Create and set up the window.
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//     Set up the content pane.
    frame.setBackground(Color.black);
    addComponentsToPane(frame.getContentPane());
//     Use the content pane's default BorderLayout. No need for
//     setLayout(new BorderLayout());
//     Display the window.

    frame.pack();
    frame.setSize(new Dimension(700, 800));
    frame.setVisible(true);
    frame.setFocusable(true);
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

    frame.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {}

          @Override
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = true;
            else runTetris.keyboardInput(e, shift);
          }

          @Override
          public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = false;
          }
        });

    timer = new Timer(1000, e -> runTetris.dropCurrentPiece());
    timer.start();

    timer2 = new Timer(200, e -> runTetris.dropSinkingPieces());
    timer2.start();
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


