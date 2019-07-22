package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;

class TetrisGUI {
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

    if (RIGHT_TO_LEFT) {
      pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    JButton button = new JButton("START)");
    pane.add(button, BorderLayout.PAGE_START);

    // Make the center component big, since that's the
    // typical usage of BorderLayout.
    bc = new BoardCompositer(runTetris);
    bc.setSize(new Dimension(400, 700));
    //    button = new JButton("Button 2 (CENTER)");
    //    button.setPreferredSize(new Dimension(200, 100));
    pane.add(bc, BorderLayout.CENTER);

    button = new JButton("START");
    pane.add(button, BorderLayout.LINE_START);

    button = new JButton("END");
    pane.add(button, BorderLayout.PAGE_END);

    button = new JButton("END");

    pane.add(button, BorderLayout.LINE_END);
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private void createAndShowGUI() {
    // Create and set up the window.
    JFrame frame = new JFrame("BorderLayoutDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Set up the content pane.
    addComponentsToPane(frame.getContentPane());
    // Use the content pane's default BorderLayout. No need for
    // setLayout(new BorderLayout());
    // Display the window.
    frame.pack();
    frame.setSize(new Dimension(500, 800));
    frame.setVisible(true);
  }

  void init() {
    /* Use an appropriate Look and Feel */
    try {
      //      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    } catch (UnsupportedLookAndFeelException
        | IllegalAccessException
        | InstantiationException
        | ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    /* Turn off metal's use bold fonts */
    UIManager.put("swing.boldMetal", Boolean.FALSE);

    // Schedule a job for the event dispatch thread:
    // creating and showing this application's GUI.
    SwingUtilities.invokeLater(this::createAndShowGUI);
  }

  BoardCompositer getBoardCompositor() {
    return bc;
  }
}
