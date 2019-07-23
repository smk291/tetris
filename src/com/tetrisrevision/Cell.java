package com.tetrisrevision;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * **
 *
 * <p>This represent a unit on the tetris playfield. Eventually it'll include color.
 *
 * <p>**
 */
public class Cell extends Point {
  private boolean empty;
  private Color color;

  Cell(Point pt, Color color) {
    this.x = (int) pt.getX();
    this.y = (int) pt.getY();
    this.color = color;
  }

  Cell(int x, int y) {
    this.empty = true;
    this.x = x;
    this.y = y;
  }

  Cell(int x, int y, Color c) {
    this.x = x;
    this.y = y;
    this.color = c;
  }

  boolean isEmpty() {
    return this.empty;
  }

  void setEmpty(boolean status) {
    this.empty = status;
  }

  public boolean isFull() {
    return !this.empty;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  public String printCell() {
    return "{ " + x + ", " + y + " }";
  }
}
