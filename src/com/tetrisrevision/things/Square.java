package com.tetrisrevision.things;

import java.awt.*;

//
//

/**
 * Four of them comprise a tetromino.
 * A Square is a colored square that fills a cell on the tetris playfield. A Square is always
 * contained in a Row, which provides the Square's `y` value.
 * `x` represents the position on the x-axis of the square in a Cartesian coordinate system.
 * `c` represent the color of the square that will be drawn at the `Square`'s position in the playfield.
 */


public final class Square implements Cloneable {
  private final Color c;
  private final int x;

  public Square(int x, Color c) {
    this.x = x;
    this.c = c;
  }

  public int getX() {
    return x;
  }

  public Color getColor() {
    return c;
  }

  public Square clone() throws CloneNotSupportedException {
    return (Square) super.clone();
  }
}
