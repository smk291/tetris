package com.tetris.tests.unit.things.tetrominoes;

import com.tetris.game.things.RowList;
import com.tetris.game.things.Tetromino;
import com.tetris.game.things.tetrominoes.*;
import com.tetris.tests.unit.UnitTestHelper;

import java.awt.*;
import java.util.HashMap;

class TTestData {
  static final int[] center = {3, 4};
  static final Tetromino[] tetrominoes = {
    new IBlock(), new JBlock(), new LBlock(), new OBlock(), new SBlock(), new TBlock(), new ZBlock()
  };
  static final RowList[][] expectedShapes = ExpectedShapes.shapesByBlock;
  static final Color[] colors = {
    new IBlock().getColor(),
    new JBlock().getColor(),
    new LBlock().getColor(),
    new OBlock().getColor(),
    new SBlock().getColor(),
    new TBlock().getColor(),
    new ZBlock().getColor()
  };

  static HashMap<Integer, HashMap<Integer, int[][]>> getKick(int i) {
    if (tetrominoes[i] instanceof IBlock) return ExpectedKickValues.iblock;
    else return ExpectedKickValues.noniblock;
  }
}

// https://tetris.wiki/Super_Rotation_System
abstract class ExpectedKickValues {
  static final HashMap<Integer, HashMap<Integer, int[][]>> noniblock =
      new HashMap<>() {
        {
          put(
              1,
              new HashMap<>() {
                {
                  put(0, new int[][] {{+1, 0}, {+1, -1}, {0, +2}, {+1, +2}});
                  put(2, new int[][] {{+1, 0}, {+1, -1}, {0, +2}, {+1, +2}});
                }
              });
          put(
              2,
              new HashMap<>() {
                {
                  put(1, new int[][] {{-1, 0}, {-1, +1}, {0, -2}, {-1, -2}});
                  put(3, new int[][] {{+1, 0}, {+1, +1}, {0, -2}, {+1, -2}});
                }
              });
          put(
              3,
              new HashMap<>() {
                {
                  put(2, new int[][] {{-1, 0}, {-1, -1}, {0, +2}, {-1, +2}});
                  put(0, new int[][] {{-1, 0}, {-1, -1}, {0, +2}, {-1, +2}});
                }
              });
          put(
              0,
              new HashMap<>() {
                {
                  put(3, new int[][] {{+1, 0}, {+1, +1}, {0, -2}, {+1, -2}});
                  put(1, new int[][] {{-1, 0}, {-1, +1}, {0, -2}, {-1, -2}});
                }
              });
        }
      };

  static final HashMap<Integer, HashMap<Integer, int[][]>> iblock =
      new HashMap<>() {
        {
          put(
              0,
              new HashMap<>() {
                {
                  put(1, new int[][] {{-2, 0}, {+1, 0}, {-2, -1}, {+1, +2}});
                  put(3, new int[][] {{-1, 0}, {+2, 0}, {-1, +2}, {+2, -1}});
                }
              });
          put(
              1,
              new HashMap<>() {
                {
                  put(0, new int[][] {{+2, 0}, {-1, 0}, {+2, +1}, {-1, -2}});
                  put(2, new int[][] {{-1, 0}, {+2, 0}, {-1, +2}, {+2, -1}});
                }
              });
          put(
              2,
              new HashMap<>() {
                {
                  put(1, new int[][] {{+1, 0}, {-2, 0}, {+1, -2}, {-2, +1}});
                  put(3, new int[][] {{+2, 0}, {-1, 0}, {+2, +1}, {-1, -2}});
                }
              });
          put(
              3,
              new HashMap<>() {
                {
                  put(2, new int[][] {{-2, 0}, {+1, 0}, {-2, -1}, {+1, +2}});
                  put(0, new int[][] {{+1, 0}, {-2, 0}, {+1, -2}, {-2, +1}});
                }
              });
        }
      };
}

class ExpectedShapes {
  private static final RowList[] iblock =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{2, 4}, {3, 4}, {4, 4}, {5, 4}},
            {{4, 5}, {4, 4}, {4, 3}, {4, 2}},
            {{2, 3}, {3, 3}, {4, 3}, {5, 3}},
            {{3, 5}, {3, 4}, {3, 3}, {3, 2}}
          });

  private static final RowList[] jblock =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{2, 5}, {2, 4}, {3, 4}, {4, 4}},
            {{3, 3}, {4, 5}, {3, 4}, {3, 5}},
            {{2, 4}, {3, 4}, {4, 4}, {4, 3}},
            {{3, 3}, {3, 4}, {2, 3}, {3, 5}}
          });

  private static final RowList[] lblock =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{4, 5}, {2, 4}, {3, 4}, {4, 4}},
            {{3, 5}, {3, 4}, {3, 3}, {4, 3}},
            {{2, 4}, {3, 4}, {4, 4}, {2, 3}},
            {{2, 5}, {3, 5}, {3, 4}, {3, 3}}
          });

  private static final RowList[] oblock =
      UnitTestHelper.getRowListArrays(new int[][][] {{{3, 5}, {4, 5}, {3, 4}, {4, 4}}});

  private static final RowList[] sblock =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{3, 5}, {4, 5}, {2, 4}, {3, 4}},
            {{3, 5}, {3, 4}, {4, 4}, {4, 3}},
            {{3, 4}, {4, 4}, {2, 3}, {3, 3}},
            {{2, 5}, {2, 4}, {3, 4}, {3, 3}}
          });

  private static final RowList[] tblock =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{3, 5}, {2, 4}, {3, 4}, {4, 4}},
            {{3, 5}, {3, 4}, {4, 4}, {3, 3}},
            {{2, 4}, {3, 4}, {4, 4}, {3, 3}},
            {{3, 5}, {2, 4}, {3, 4}, {3, 3}}
          });

  private static final RowList[] zblock =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{2, 5}, {3, 5}, {3, 4}, {4, 4}},
            {{4, 5}, {3, 4}, {4, 4}, {3, 3}},
            {{2, 4}, {3, 4}, {3, 3}, {4, 3}},
            {{3, 5}, {2, 4}, {3, 4}, {2, 3}}
          });

  static RowList[][] shapesByBlock = {iblock, jblock, lblock, oblock, sblock, tblock, zblock};
}
