package com.tetrisrevision.tetrominos;

import java.awt.*;

class OPiece extends Tetromino {
  private static final int[][][] offsets = {{Coords.u1, Coords.r1u1, {0, 0}, Coords.r1}};

  OPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.yellow);
  }
}
