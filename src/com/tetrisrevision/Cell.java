package com.tetrisrevision;

/**
 * **
 *
 * <p>This represent a unit on the tetris playfield. Eventually it'll include color.
 *
 * <p>**
 */
public class Cell {
  //    private Point pt;
  private boolean empty;
  //    private Color c;

  Cell() {
    this.empty = true;
  }

  //    Cell (int y, int x) {
  //        this.empty = true;
  //        this.pt = new Point(x, y);
  //    }

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
