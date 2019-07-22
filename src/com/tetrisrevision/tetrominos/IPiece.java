package com.tetrisrevision.tetrominos;

import java.awt.*;
import java.util.HashMap;

class IPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{-1, 0}, {0, 0}, {1, 0}, {2, 0}},
    {{1, -1}, {1, 0}, {1, 1}, {1, 2}},
    {{-1, 1}, {0, 1}, {1, 1}, {2, 1}},
    {{0, -1}, {0, 0}, {0, 1}, {0, 2}}
  };

  private static HashMap<Integer, HashMap<Integer, Integer[][]>> kickData =
      new HashMap<Integer, HashMap<Integer, Integer[][]>>() {
        {
          put(
              0,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(1, new Integer[][] {{0, 0}, {-2, 0}, {1, 0}, {-2, -1}, {1, 2}});
                  put(3, new Integer[][] {{0, 0}, {-1, 0}, {2, 0}, {-1, 2}, {2, -1}});
                }
              });
          put(
              1,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(0, new Integer[][] {{0, 0}, {2, 0}, {-1, 0}, {2, 1}, {-1, -2}});
                  put(2, new Integer[][] {{0, 0}, {-1, 0}, {2, 0}, {-1, 2}, {2, -1}});
                }
              });
          put(
              2,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(1, new Integer[][] {{0, 0}, {1, 0}, {-2, 0}, {1, -2}, {-2, 1}});
                  put(3, new Integer[][] {{0, 0}, {2, 0}, {-1, 0}, {2, 1}, {-1, -2}});
                }
              });
          put(
              3,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(0, new Integer[][] {{0, 0}, {1, 0}, {-2, 0}, {1, -2}, {-2, 1}});
                  put(2, new Integer[][] {{0, 0}, {-2, 0}, {1, 0}, {-2, -1}, {1, 2}});
                }
              });
        }
      };

  IPiece() {
    super.setOffsets(offsets);
    super.setKickData(kickData);
    super.setColor(Color.blue);
  }
}
