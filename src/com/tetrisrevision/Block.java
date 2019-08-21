package com.tetrisrevision;

import java.awt.Color;

/**
 * <p>A Block is a colored square that fills a cell on the tetris play field.
 *
 * <p>**
 */
public final class Block {
  private Color c;
  private double x;

  Block(double x) {
    this.x = (int) x;
  }

  Block(double x, Color c) {
    this.x = x;
    this.c = c;
  }

  double getX()
  {
    return x;
  }

  void setX(double x)
  {
    this.x = x;
  }

  public Color getColor() {
    return c;
  }

  public void setColor(final Color c) {
    this.c =  c;
  }

  public Block clone() throws CloneNotSupportedException {
    return (Block) super.clone();
  }
}
