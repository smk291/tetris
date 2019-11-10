package com.tetrisrevision.unittests.things.tetrominoes;

import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.tetrominoes.*;
import com.tetrisrevision.unittests.UnitTestHelper;

import java.awt.*;
import java.util.HashMap;

class TTestData {
  static final int[] center = {3, 4};
  static final Tetromino[] tetrominoes = {
    new IPiece(), new JPiece(), new LPiece(), new OPiece(), new SPiece(), new TPiece(), new ZPiece()
  };
  static final RowList[][] expectedShapes = ExpectedShapes.shapesByPiece;
  static final Color[] colors = {
    new IPiece().getColor(),
    new JPiece().getColor(),
    new LPiece().getColor(),
    new OPiece().getColor(),
    new SPiece().getColor(),
    new TPiece().getColor(),
    new ZPiece().getColor()
  };

  static HashMap<Integer, HashMap<Integer, int[][]>> getKick(int i) {
    if (tetrominoes[i] instanceof IPiece) return ExpectedKickValues.ipiece;
    else return ExpectedKickValues.nonipiece;
  }
}

// https://tetris.wiki/Super_Rotation_System
abstract class ExpectedKickValues {
  static final HashMap<Integer, HashMap<Integer, int[][]>> nonipiece =
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

  static final HashMap<Integer, HashMap<Integer, int[][]>> ipiece =
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
  private static final RowList[] ipiece =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{2, 4}, {3, 4}, {4, 4}, {5, 4}},
            {{4, 5}, {4, 4}, {4, 3}, {4, 2}},
            {{2, 3}, {3, 3}, {4, 3}, {5, 3}},
            {{3, 5}, {3, 4}, {3, 3}, {3, 2}}
          });

  private static final RowList[] jpiece =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{2, 5}, {2, 4}, {3, 4}, {4, 4}},
            {{3, 3}, {4, 5}, {3, 4}, {3, 5}},
            {{2, 4}, {3, 4}, {4, 4}, {4, 3}},
            {{3, 3}, {3, 4}, {2, 3}, {3, 5}}
          });

  private static final RowList[] lpiece =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{4, 5}, {2, 4}, {3, 4}, {4, 4}},
            {{3, 5}, {3, 4}, {3, 3}, {4, 3}},
            {{2, 4}, {3, 4}, {4, 4}, {2, 3}},
            {{2, 5}, {3, 5}, {3, 4}, {3, 3}}
          });

  private static final RowList[] opiece =
      UnitTestHelper.getRowListArrays(new int[][][] {{{3, 5}, {4, 5}, {3, 4}, {4, 4}}});

  private static final RowList[] spiece =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{3, 5}, {4, 5}, {2, 4}, {3, 4}},
            {{3, 5}, {3, 4}, {4, 4}, {4, 3}},
            {{3, 4}, {4, 4}, {2, 3}, {3, 3}},
            {{2, 5}, {2, 4}, {3, 4}, {3, 3}}
          });

  private static final RowList[] tpiece =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{3, 5}, {2, 4}, {3, 4}, {4, 4}},
            {{3, 5}, {3, 4}, {4, 4}, {3, 3}},
            {{2, 4}, {3, 4}, {4, 4}, {3, 3}},
            {{3, 5}, {2, 4}, {3, 4}, {3, 3}}
          });

  private static final RowList[] zpiece =
      UnitTestHelper.getRowListArrays(
          new int[][][] {
            {{2, 5}, {3, 5}, {3, 4}, {4, 4}},
            {{4, 5}, {3, 4}, {4, 4}, {3, 3}},
            {{2, 4}, {3, 4}, {3, 3}, {4, 3}},
            {{3, 5}, {2, 4}, {3, 4}, {2, 3}}
          });

  static RowList[][] shapesByPiece = {ipiece, jpiece, lpiece, opiece, spiece, tpiece, zpiece};
}
