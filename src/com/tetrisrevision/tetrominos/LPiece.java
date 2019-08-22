package com.tetrisrevision.tetrominos;

import java.awt.*;

class LPiece extends Tetromino {
  private static final int[][][] offsets = {
    {Coords.r1u1, Coords.l1, {0, 0}, Coords.r1},
    {Coords.u1, {0, 0}, Coords.d1, Coords.r1d1},
    {Coords.l1, {0, 0}, Coords.r1, Coords.l1d1},
    {Coords.l1u1, Coords.u1, {0, 0}, Coords.d1}
  };

  LPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.orange);
  }
}
