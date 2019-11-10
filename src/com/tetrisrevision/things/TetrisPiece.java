package com.tetrisrevision.things;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.recordkeeping.TSpinTracker;
import com.tetrisrevision.things.tetrominoes.Tetromino;

import java.util.Arrays;
import java.util.HashMap;

public class TetrisPiece {
  private int rotation;
  private int prevRotation;
  private Center center;
  private Tetromino tetromino;
  private TSpinTracker tSpinTracker;
  public TetrisPiece() {}

  public TetrisPiece(Tetromino t) {
    reset();
    tetromino = t;
  }

  public void reset(Tetromino t) {
    reset();
    tetromino = t;
  }

  public void reset() {
    center = new Center(4, Constants.topRow);
    rotation = 0;
    prevRotation = 0;
    tSpinTracker = new TSpinTracker();
  }

  public int getRotation() {
    return rotation;
  }

  public void setRotation(int i) {
    rotation = i;
  }

  public void resetRotation() {
    rotation = 0;
  }

  public void incrementRotation(int incr) {
    prevRotation = rotation;
    rotation += incr;

    if (rotation < 0) {
      rotation = getRotationMax() - 1;
    } else if (rotation >= getRotationMax()) {
      rotation = 0;
    }
  }

  public int getPrevRotation() {
    return prevRotation;
  }

  public RowList getBlocks() {
    RowList rows = new RowList();

    Arrays.stream(tetromino.getOffsets()[rotation])
        .forEach(
            p -> {
              int x = center.getX() + p[0];
              int y = center.getY() + p[1];

              Block b = new Block(x, tetromino.getColor());

              rows.addBlock(y, b);
            });

    return rows;
  }

  private int getRotationMax() {
    return tetromino.getOffsets().length;
  }

  public HashMap<Integer, HashMap<Integer, int[][]>> getKickData() {
    return tetromino.getKickData();
  }

  public void setCenter(int x, int y) {
    center = new Center(x, y);
  }

  public Center getCenter() {
    return center;
  }

  public void setCenter(Center pt) {
    center = pt;
  }

  public Tetromino getTetromino() {
    return tetromino;
  }

  public void setTetromino(Tetromino t) {
    tetromino = t;
  }

  public TSpinTracker gettSpinTracker() {
    return tSpinTracker;
  }

  public void setTSpinTracker(TSpinTracker tSpinTracker) {
    this.tSpinTracker = tSpinTracker;
  }
}
