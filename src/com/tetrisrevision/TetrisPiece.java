package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class TetrisPiece {
  private int prevRotation;
  private int rotation;
  private Point center;
  private Tetromino tetromino;
  private boolean addToBoard;

  public TetrisPiece () {}

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
    addToBoard = false;
  }

  void setTetromino(Tetromino t) {
    this.tetromino = t;
  }

  int getRotation() {
    return this.rotation;
  }

  void setRotation(int o) {
    this.rotation = o;
  }

  void incrementRotation(int i) {
    this.rotation += i;
  }

  int getPrevRotation() {
    return prevRotation;
  }

  void setPrevRotation(int r) {
    this.prevRotation = r;
  }

  Point[] getPoints() {
    return Arrays.stream(tetromino.getOffsets()[rotation])
        .map(p -> new Point((int) this.center.getX() + p[0], (int) this.center.getY() + p[1]))
        .toArray(Point[]::new);
  }

  int getRotationMax() {
    return tetromino.getOffsets().length;
  }

  HashMap<Integer, HashMap<Integer, Integer[][]>> getKickData() {
    return tetromino.getKickData();
  }

  void setCenter(int x, int y) {
    this.center = new Point(x, y);
  }

  boolean getAddToBoard() {
    return addToBoard;
  }

  void setAddToBoard(boolean addToBoard) {
    this.addToBoard = addToBoard;
  }

  Point getCenter() {
    return center;
  }

  void setCenter(Point pt) {
    this.center = pt;
  }

  Cell[] getCells() {
    return Arrays.stream(getPoints()).map(pt -> new Cell(pt, tetromino.getColor())).toArray(Cell[]::new);
  }

  Tetromino getTetromino() {
    return tetromino;
  }

  boolean pieceIsAtEdge() {
    return Arrays.stream(getCells()).anyMatch(c -> (int) c.getX() == 0 || (int) c.getX() == 9);
  }
}
