package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;

class TetrisGUI {
  private Timer timer;
  private Timer timer2;
  private JFrame frame = new JFrame("Tetris");
  private boolean RIGHT_TO_LEFT = false;
  private RunTetris runTetris;
  BoardCompositer bc = new BoardCompositer(runTetris);

  TetrisGUI(RunTetris runTetris) {
    this.runTetris = runTetris;
  }

  private void addComponentsToPane(Container pane) {
    if (!(pane.getLayout() instanceof BorderLayout)) {
      pane.add(new JLabel("Container doesn't use BorderLayout!"));

      return;
    }

    bc = new BoardCompositer(runTetris);

    pane.add(bc, BorderLayout.CENTER);

    JButton button = new JButton("START");

    button.addActionListener(e -> runTetris.getRecordKeeping().setLevel(0, this));
    pane.add(button, BorderLayout.LINE_START);
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
          public void keyTyped(KeyEvent e) {
            runTetris.keyboardInput(e.getKeyChar());
          }

          @Override
          public void keyPressed(KeyEvent e) {}

          @Override
          public void keyReleased(KeyEvent e) {}
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

  BoardCompositer getBoardCompositor() {
    return bc;
  }
}
