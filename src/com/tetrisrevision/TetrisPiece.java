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
    tetromino = t;
  }

  void reset(Tetromino t) {
    reset();
    tetromino = t;
  }

  private void reset() {
    center = new Point(4, Constants.topRow());
    rotation = 0;
    prevRotation = 0;
    tSpinTracker = new TSpinTracker();
  }

  int getRotation() {
    return rotation;
  }

  void resetRotation() {
    rotation = 0;
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

  RowList getBlocks() {
    RowList rows = new RowList();

    Arrays.stream(tetromino.getOffsets()[rotation])
        .forEach(p -> {
          double x = center.getX() + p[0];
          double y = center.getY() + p[1];

          Block b = new Block(x, tetromino.getColor());

          rows.addBlock(y, b);
        });

    return rows;
  }

  private int getRotationMax() {
    return tetromino.getOffsets().length;
  }

  HashMap<Integer, HashMap<Integer, int[][]>> getKickData() {
    return tetromino.getKickData();
  }

  void setCenter(int x, int y) {
    center = new Point(x, y);
  }

  Point getCenter() {
    return center;
  }

  void setCenter(Point pt) {
    center = pt;
  }

  Tetromino getTetromino() {
    return tetromino;
  }

  void setTetromino(Tetromino t) {
    tetromino = t;
  }

  TSpinTracker gettSpinTracker() {
    return tSpinTracker;
  }

  public void setTSpinTracker(TSpinTracker tSpinTracker) {
    this.tSpinTracker = tSpinTracker;
  }
}
