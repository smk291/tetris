package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Tetris {

  public static void main(String[] args) {
    RunTetris g = new RunTetris(10, 24);
    TetrisGUI tetrisGUI = new TetrisGUI(g);
    g.setTetrisGUI(tetrisGUI);
    tetrisGUI.init();
  }
}

