package com.tetrisrevision.tetrominos;

import java.awt.*;

class LPiece extends Tetromino {
  private static final int[][][] offsets = {
    {RelativeCoords.r1u1, RelativeCoords.l1, {0, 0}, RelativeCoords.r1},
    {RelativeCoords.u1, {0, 0}, RelativeCoords.d1, RelativeCoords.r1d1},
    {RelativeCoords.l1, {0, 0}, RelativeCoords.r1, RelativeCoords.l1d1},
    {RelativeCoords.l1u1, RelativeCoords.u1, {0, 0}, RelativeCoords.d1}
  };

  LPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.orange);
  }
}
