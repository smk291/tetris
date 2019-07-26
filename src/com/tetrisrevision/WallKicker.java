package com.tetrisrevision;

import java.util.HashMap;

abstract class WallKicker {
  static Integer tryKick(TetrisPiece piece, Blocks2d field) {
    HashMap<Integer, HashMap<Integer, Integer[][]>> kickData = piece.getKickData();

    if (null == kickData) return null;

    HashMap<Integer, Integer[][]> kickOffsets1 = kickData.get(piece.getPrevRotation());

    if (kickOffsets1 == null) return null;

    Integer[][] kickOffsets2 = kickOffsets1.get(piece.getRotation());

    for (int i = 0; i < kickOffsets2[i].length; i++) {
      Integer[] offset = kickOffsets2[i];

      piece.getCenter().translate(offset[0], offset[1]);

      if (PlacementTester.cellsCanBeOccupied(piece, field)) return i;

      piece.getCenter().translate(-offset[0], -offset[1]);
    }

    return null;
  }
}
