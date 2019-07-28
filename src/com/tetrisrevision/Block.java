package com.tetrisrevision;

import java.awt.*;

/**
 * **
 *
 * <p>This represent a unit on the tetris playfield. Eventually it'll include color.
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

  public String printCell() {
    return "{ " + x + ", " + y + " }";
  }
}
