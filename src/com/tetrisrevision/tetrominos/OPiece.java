package com.tetrisrevision.tetrominos;

import java.awt.*;

class OPiece extends Tetromino {
  private static final int[][][] offsets = {
    {RelativeCoords.u1, RelativeCoords.r1u1, {0, 0}, RelativeCoords.r1}
  };

  OPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.yellow);
  }
}
