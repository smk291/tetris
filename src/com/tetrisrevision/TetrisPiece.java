package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class TetrisPiece {
  private int rotation;
  private int prevRotation;
  private Point center;
  private Tetromino tetromino;
  private TSpinTracker tSpinTracker;

  public TetrisPiece() {}

  public TetrisPiece(Tetromino t) {
    reset();
    this.tetromino = t;
  }

  void reset(Tetromino t) {
    reset();
    this.tetromino = t;
  }

  private void reset() {
    this.center = new Point(4, 0);
    this.rotation = 0;
    this.prevRotation = 0;
    this.tSpinTracker = new TSpinTracker();
  }

  int getRotation() {
    return this.rotation;
  }

  void setRotation(int o) {
    this.rotation = o;
  }

  void incrementRotation(int incr) {
    prevRotation = rotation;
    rotation += incr;

    if (rotation < 0) {
      rotation = getRotationMax() - 1;
    } else if (rotation >= getRotationMax()) {
      rotation = 0;
    }
  }

  int getPrevRotation() {
    return prevRotation;
  }

  void setPrevRotation(int r) {
    this.prevRotation = r;
  }

  private Point[] getPoints() {
    return Arrays.stream(tetromino.getOffsets()[rotation])
        .map(p -> new Point((int) this.center.getX() + p[0], (int) this.center.getY() + p[1]))
        .toArray(Point[]::new);
  }

  private int getRotationMax() {
    return tetromino.getOffsets().length;
  }

  HashMap<Integer, HashMap<Integer, Integer[][]>> getKickData() {
    return tetromino.getKickData();
  }

  void setCenter(int x, int y) {
    this.center = new Point(x, y);
  }

  Point getCenter() {
    return center;
  }

  void setCenter(Point pt) {
    this.center = pt;
  }

  Cell[] getCells() {
    return Arrays.stream(getPoints())
        .map(pt -> new Cell(pt, tetromino.getColor()))
        .toArray(Cell[]::new);
  }

  Tetromino getTetromino() {
    return tetromino;
  }

  void setTetromino(Tetromino t) {
    this.tetromino = t;
  }

  public TSpinTracker gettSpinTracker() {
    return tSpinTracker;
  }

  public void settSpinTracker(TSpinTracker tSpinTracker) {
    this.tSpinTracker = tSpinTracker;
  }
}

