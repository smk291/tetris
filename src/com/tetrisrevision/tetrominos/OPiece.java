package com.tetrisrevision.tetrominos;

import java.awt.*;

class OPiece extends Tetromino {
  private static final int[][][] offsets = {{{0, -1}, {1, -1}, {0, 0}, {1, 0}}};

  OPiece() {
    super.setOffsets(offsets);
    super.setColor(new Color	(238, 237, 9));
  }
}
