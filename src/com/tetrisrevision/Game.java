package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Game {
  private RunTetris runTetris;
  private static int width = 10;
  private static int height = 24;
  private static int[] delayPerLevel = {500, 450, 400, 350, 300, 250, 200, 150, 100, 50};
  private Timer t;
  private int currentLevel;

  Game() {
    runTetris = new RunTetris(width, height);

    ActionListener e = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        runTetris.dropCurrentPiece();
      }
    };

    t = new Timer(delayPerLevel[0], e);
  }
}
