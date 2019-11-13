package com.tetrisrevision.actions;

import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.ActiveBlock;
import com.tetrisrevision.things.tetrominoes.OBlock;

import java.util.HashMap;

public abstract class WallKicker {
  public static Integer tryKick(ActiveBlock block, RowList field) {
    HashMap<Integer, HashMap<Integer, int[][]>> kickData = block.getKickData();

    if (null == kickData || block.getTetromino() instanceof OBlock) return null;

    HashMap<Integer, int[][]> kickOffsets1 = kickData.get(block.getPrevRotation());

    if (kickOffsets1 == null) return null;

    int[][] kickOffsets2 = kickOffsets1.get(block.getRotation());

    for (int i = 0; i < kickOffsets2.length; i++) {
      int[] offset = kickOffsets2[i];

      block.getCenter().translate(offset[0], offset[1]);

      if (PlacementTester.cellsCanBeOccupied(block, field)) return i;

      block.getCenter().translate(-offset[0], -offset[1]);
    }

    return null;
  }
}
