package com.tetrisrevision.actions;

import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.unittests.UnitTestHelper;

import java.util.HashMap;

public abstract class WallKicker {
  public static Integer tryKick(TetrisPiece piece, RowList field) {
    HashMap<Integer, HashMap<Integer, int[][]>> kickData = piece.getKickData();

    if (null == kickData) return null;

    HashMap<Integer, int[][]> kickOffsets1 = kickData.get(piece.getPrevRotation());

    if (kickOffsets1 == null) return null;

    int[][] kickOffsets2 = kickOffsets1.get(piece.getRotation());

    for (int i = 0; i < kickOffsets2.length; i++) {
      int[] offset = kickOffsets2[i];

      piece.getCenter().translate(offset[0], offset[1]);

      if (PlacementTester.cellsCanBeOccupied(piece, field)) return i;

      piece.getCenter().translate(-offset[0], -offset[1]);
    }

    return null;
  }
}
