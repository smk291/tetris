package com.tetrisrevision;

import java.awt.*;

/**
 * **
 *
 * <p>A Block is a colored square that fills a cell on the tetris play field.
 *
 * <p>**
 */
public class Block extends Point {
  private Color color;

  Block(double x, double y) {
    this.x = (int) x;
    this.y = (int) y;
  }

  void setY(double y) {
    this.y = (int) y;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
