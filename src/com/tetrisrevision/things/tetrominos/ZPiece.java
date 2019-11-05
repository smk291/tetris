package com.tetrisrevision.things.tetrominos;

import com.tetrisrevision.helpers.RelativeCoords;

import java.awt.*;

class ZPiece extends Tetromino {
  private static final int[][][] offsets = {
    {RelativeCoords.l1u1, RelativeCoords.u1, RelativeCoords.center, RelativeCoords.r1},
    {RelativeCoords.r1u1, RelativeCoords.center, RelativeCoords.r1, RelativeCoords.d1},
    {RelativeCoords.l1, RelativeCoords.center, RelativeCoords.d1, RelativeCoords.r1d1},
    {RelativeCoords.u1, RelativeCoords.l1, RelativeCoords.center, RelativeCoords.l1d1}
  };

  ZPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.red);
  }
}
