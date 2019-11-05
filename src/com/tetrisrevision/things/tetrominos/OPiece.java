package com.tetrisrevision.things.tetrominos;

import com.tetrisrevision.helpers.RelativeCoords;

import java.awt.*;

class OPiece extends Tetromino {
  private static final int[][][] offsets = {{RelativeCoords.u1, RelativeCoords.r1u1, RelativeCoords.center, RelativeCoords.r1}};

  OPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.yellow);
  }
}
