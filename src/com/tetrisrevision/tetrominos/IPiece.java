package com.tetrisrevision.tetrominos;

import com.tetrisrevision.TetrisConstants;

import java.awt.*;
import java.util.HashMap;

class IPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{TetrisConstants.left(), 0}, {0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.right() * 2, 0}},
    {{TetrisConstants.right(), TetrisConstants.up()}, {TetrisConstants.right(), 0}, {TetrisConstants.right(), TetrisConstants.down()}, {TetrisConstants.right(), TetrisConstants.down() * TetrisConstants.up() * 2}},
    {{TetrisConstants.left(), TetrisConstants.down()}, {0, TetrisConstants.down()}, {TetrisConstants.right(), TetrisConstants.down()}, {TetrisConstants.right() * 2, TetrisConstants.down()}},
    {{0, TetrisConstants.up()}, {0, 0}, {0, TetrisConstants.down()}, {0, TetrisConstants.up() * 2}}
  };

  private static HashMap<Integer, HashMap<Integer, Integer[][]>> kickData =
      new HashMap<Integer, HashMap<Integer, Integer[][]>>() {
        {
          put(
              0,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(1, new Integer[][] {{0, 0}, {TetrisConstants.left() * 2, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.left() * 2, TetrisConstants.up()}, {TetrisConstants.right(), TetrisConstants.up() * 2}});
                  put(3, new Integer[][] {{0, 0}, {TetrisConstants.left(), 0}, {TetrisConstants.right() * 2, 0}, {TetrisConstants.left(), TetrisConstants.up() * 2}, {TetrisConstants.right() * 2, TetrisConstants.up()}});
                }
              });
          put(
              1,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(0, new Integer[][] {{0, 0}, {TetrisConstants.right() * 2, 0}, {TetrisConstants.left(), 0}, {TetrisConstants.right() * 2, TetrisConstants.down()}, {TetrisConstants.left(), -TetrisConstants.up() * 2}});
                  put(2, new Integer[][] {{0, 0}, {TetrisConstants.left(), 0}, {TetrisConstants.right() * 2, 0}, {TetrisConstants.left(), TetrisConstants.up() * 2}, {TetrisConstants.right() * 2, TetrisConstants.up()}});
                }
              });
          put(
              2,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(1, new Integer[][] {{0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.left() * 2, 0}, {TetrisConstants.right(), -TetrisConstants.up() * 2}, {TetrisConstants.left() * 2, TetrisConstants.down()}});
                  put(3, new Integer[][] {{0, 0}, {TetrisConstants.right() * 2, 0}, {TetrisConstants.left(), 0}, {TetrisConstants.right() * 2, TetrisConstants.down()}, {TetrisConstants.left(), -TetrisConstants.up() * 2}});
                }
              });
          put(
              3,
              new HashMap<Integer, Integer[][]>() {
                {
                  put(0, new Integer[][] {{0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.left() * 2, 0}, {TetrisConstants.right(), -TetrisConstants.up() * 2}, {TetrisConstants.left() * 2, TetrisConstants.down()}});
                  put(2, new Integer[][] {{0, 0}, {TetrisConstants.left() * 2, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.left() * 2, TetrisConstants.up()}, {TetrisConstants.right(), TetrisConstants.up() * 2}});
                }
              });
        }
      };

  IPiece() {
    super.setOffsets(offsets);
    super.setKickData(kickData);
    super.setColor(Color.CYAN);
  }
}
