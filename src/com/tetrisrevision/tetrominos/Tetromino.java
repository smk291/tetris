package com.tetrisrevision.tetrominos;

import java.awt.*;
import java.util.HashMap;

public class Tetromino {
  private int[][][] offsets;
  private Color color;

  private HashMap<Integer, HashMap<Integer, Integer[][]>> kickData =
      new HashMap<Integer, HashMap<Integer, Integer[][]>>() {
        {
          put(
              0,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(1, new Integer[][] {{0, 0}, {-1, 0}, {-1, 1}, {0, -2}, {-1, -2}});
                  put(3, new Integer[][] {{0, 0}, {1, 0}, {1, 1}, {0, -2}, {1, -2}});
                }
              });
          put(
              1,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(0, new Integer[][] {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}});
                  put(2, new Integer[][] {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}});
                }
              });
          put(
              2,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(1, new Integer[][] {{0, 0}, {-1, 0}, {-1, 1}, {0, -2}, {-1, -2}});
                  put(3, new Integer[][] {{0, 0}, {1, 0}, {1, 1}, {0, -2}, {1, -2}});
                }
              });
          put(
              3,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(0, new Integer[][] {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}});
                  put(2, new Integer[][] {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}});
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
}
