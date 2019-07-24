package com.tetrisrevision;

import java.util.HashMap;

abstract class WallKicker {
  static boolean tryKick(TetrisPiece piece, Blocks2d field) {
    HashMap<Integer, HashMap<Integer, Integer[][]>> kickData = piece.getKickData();

    if (null == kickData) return false;

    HashMap<Integer, Integer[][]> kickOffsets1 = kickData.get(piece.getPrevRotation());

    if (kickOffsets1 == null) return false;

    Integer[][] kickOffsets2 = kickOffsets1.get(piece.getRotation());

    for (Integer[] offset : kickOffsets2) {
      piece.getCenter().translate(offset[0], offset[1]);

      if (CellTester.emptyAndInBoundsAndNoOverlapNoMin(piece, field)) {
        return true;
      }

      piece.getCenter().translate(-offset[0], -offset[1]);
    }

    return false;
  }
}
