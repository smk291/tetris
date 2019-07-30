package com.tetrisrevision;

import java.awt.Point;
import java.awt.Color;

/**
 * <p>A Block is a colored square that fills a cell on the tetris play field.
 *
 * <p>**
 */
public final class Block extends Point {
  /**
   * @color is the color of the block
   */
  private Color color;

  Block(final double x, final double y) {
    this.x = (int) x;
    this.y = (int) y;
  }

  /**
   * @param y - y coordinate on playfield
   */
  void setY(final double y) {
    this.y = (int) y;
  }

  /**
   * @return color of block
   */

  public Color getColor() {
    return color;
  }

  /**
   * @param c color of block
   */

  public void setColor(final Color c) {
    this.color =  c;
  }
}
