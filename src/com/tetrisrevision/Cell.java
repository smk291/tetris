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
  private Color c;
  private Rectangle2D.Double rect;

  Cell() {
    this.empty = true;
  }

  Cell(Point pt, Color c) {
    this.x = (int) pt.getX();
    this.y = (int) pt.getY();
    this.c = c;
  }

  Cell(int x, int y) {
    this.empty = true;
    this.x = x;
    this.y = y;
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
}
