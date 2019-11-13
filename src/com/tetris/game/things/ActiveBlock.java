package com.tetris.game.things;

import com.tetris.game.constants.Constants;
import com.tetris.game.recordkeeping.TSpinTracker;

import java.util.Arrays;
import java.util.HashMap;

/**
 * `TetrisPiece` is the actively falling tetromino, the tetromino (or block) that the user manipulates. It spawns on
 * 20th row from the bottom. Rather than store the position of all four squares, and move all four squares each time
 * the user rotates or moves the block, `TetrisPiece` stores a single set of coordinates. When the block moves or
 * rotates, the program gets absolute coordiantes by getting the appropriate offsets (relative coordinates) from the
 * tetromino and converting them to a `RowList`.
 *
 * Apart from that one method (`getSquares`), this is basically a data class that stores everything the program needs in
 * order to do everything the program does to and with tetrominos.
 *
 * NOTE: Maybe this should extend the Tetromino class.
 */
public class ActiveBlock {
  private int rotation;
  private int prevRotation;
  private Center center;
  private Tetromino tetromino;
  private TSpinTracker tSpinTracker;
  public ActiveBlock() {}

  public ActiveBlock(Tetromino t) {
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

  public RowList getSquares() {
    RowList rows = new RowList();

    Arrays.stream(tetromino.getOffsets()[rotation])
        .forEach(
            p -> {
              int x = center.getX() + p[0];
              int y = center.getY() + p[1];

              Square b = new Square(x, tetromino.getColor());

              rows.addSquare(y, b);
            });

    return rows;
  }

  public int getRotationMax() {
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

  public void setPrevRotation(int prevRotation) {
    this.prevRotation = prevRotation;
  }
}
