package com.tetrisrevision.things;

import java.awt.*;

public class Center {
  private int x;
  private int y;

  public Center() { }

  public Center(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void translate(int dx, int dy) {
    this.x += dx;
    this.y += dy;
  }
}
