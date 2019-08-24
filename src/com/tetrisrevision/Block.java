package com.tetrisrevision;

import java.awt.*;

// A Block is a colored square that fills a cell on the tetris play field. A Block is always contained in a Row.
public final class Block implements Cloneable {
  private final Color c;
  private final double x;

  Block(double x, Color c) {
    this.x = x;
    this.c = c;
  }

  double getX() {
    return x;
  }

  public Color getColor() {
    return c;
  }

  public Block clone() throws CloneNotSupportedException {
    return (Block) super.clone();
  }
}