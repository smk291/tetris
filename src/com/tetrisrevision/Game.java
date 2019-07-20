package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

class Game {
  private static ArrayList<ArrayList<Point>> sinkingPieces = new ArrayList<>();
  private RunTetris runTetris;
  private static int width = 10;
  private static int height = 24;

  Game() {
    runTetris = new RunTetris(width, height);
  }

  void play() {
    while (runTetris.continueGame()) {
      runTetris.keyboardInput();
    }
  }
}
