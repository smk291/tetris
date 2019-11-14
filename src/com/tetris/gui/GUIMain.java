package com.tetris.gui;

import com.tetris.game.RunTetris;
import com.tetris.gui.constants.GUIConstants;
import com.tetris.gui.input.HandleInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUIMain {
  private boolean shift = false;
  private RunTetris runTetris;
  private JFrame tetrisFrame;

  public GUIMain() {}

  private void createAndShowGUI() {
    runTetris = new RunTetris(this);
    tetrisFrame = new MainJFrame(runTetris);
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
  }

  private void keyboardInput(KeyEvent e, boolean shift) {
    HandleInput.keyboardInput(runTetris, e, shift);
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

  public void repaintFrame() {
    tetrisFrame.repaint();
  }
}
