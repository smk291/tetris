package com.tetrisrevision;

public class Tetris {
  public static void main(String[] args) {
    RunTetris g = new RunTetris(TetrisConstants.width(), TetrisConstants.height());
    TetrisGUI tetrisGUI = new TetrisGUI(g);
    g.setTetrisGUI(tetrisGUI);
    tetrisGUI.init();
  }
}
