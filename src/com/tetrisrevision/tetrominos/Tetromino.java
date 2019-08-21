package com.tetrisrevision.tetrominos;

import com.tetrisrevision.TetrisConstants;
import java.awt.*;
import java.util.HashMap;

public class Tetromino {
  private int[][][] offsets;
  private Color color;
  private boolean tPiece = false;

  private HashMap<Integer, HashMap<Integer, Integer[][]>> kickData =
      new HashMap<Integer, HashMap<Integer, Integer[][]>>() {
        {
          put(
              0,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(1, new Integer[][] {{0, 0}, {TetrisConstants.left(), 0}, {TetrisConstants.left(), TetrisConstants.down()}, {0, TetrisConstants.up() * 2}, {TetrisConstants.left(), TetrisConstants.up() * 2}});
                  put(3, new Integer[][] {{0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.right(), TetrisConstants.down()}, {0, TetrisConstants.up() * 2}, {TetrisConstants.right(), TetrisConstants.up() * 2}});
                }
              });
          put(
              1,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(0, new Integer[][] {{0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.right(), TetrisConstants.up()}, {0, TetrisConstants.down() * 2}, {TetrisConstants.right(), TetrisConstants.down() * 2}});
                  put(2, new Integer[][] {{0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.right(), TetrisConstants.up()}, {0, TetrisConstants.down() * 2}, {TetrisConstants.right(), TetrisConstants.down() * 2}});
                }
              });
          put(
              2,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(1, new Integer[][] {{0, 0}, {TetrisConstants.left(), 0}, {TetrisConstants.left(), TetrisConstants.down()}, {0, TetrisConstants.up() * 2}, {TetrisConstants.left(), TetrisConstants.up() * 2}});
                  put(3, new Integer[][] {{0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.right(), TetrisConstants.down()}, {0, TetrisConstants.up() * 2}, {TetrisConstants.right(), TetrisConstants.up() * 2}});
                }
              });
          put(
              3,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(0, new Integer[][] {{0, 0}, {TetrisConstants.left(), 0}, {TetrisConstants.left(), TetrisConstants.up()}, {0, TetrisConstants.down() * 2}, {TetrisConstants.left(), TetrisConstants.down() * 2}});
                  put(2, new Integer[][] {{0, 0}, {TetrisConstants.left(), 0}, {TetrisConstants.left(), TetrisConstants.up()}, {0, TetrisConstants.down() * 2}, {TetrisConstants.left(), TetrisConstants.down() * 2}});
                }
              });
        }
      };

  public int[][][] getOffsets() {
    return this.offsets;
  }

  void setOffsets(int[][][] offsets) {
    this.offsets = offsets;
  }

  public HashMap<Integer, HashMap<Integer, Integer[][]>> getKickData() {
    return kickData;
  }

  void setKickData(HashMap<Integer, HashMap<Integer, Integer[][]>> kickData) {
    this.kickData = kickData;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public boolean isTPiece() {
    return this.tPiece;
  }

  public void setTPiece(boolean b) {
    this.tPiece = b;
  }
}
