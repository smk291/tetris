package com.tetrisrevision;

import java.awt.Color;

/**
 * <p>A Block is a colored square that fills a cell on the tetris play field.
 *
 * <p>**
 */
public final class Block {
  private final Color c;
  private final double x;

  Block(double x, Color c) {
    this.x = x;
    this.c = c;
  }

  double getX()
  {
    return x;
  }

  public Color getColor() {
    return c;
  }

  public Block clone() throws CloneNotSupportedException {
    return (Block) super.clone();
  }
}
