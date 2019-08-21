package com.tetrisrevision.tetrominos;

import com.tetrisrevision.TetrisConstants;
import java.awt.*;

class OPiece extends Tetromino {
  private static final int[][][] offsets = {{{0, TetrisConstants.up()}, {TetrisConstants.right(), TetrisConstants.up()}, {0, 0}, {TetrisConstants.right(), 0}}};

  OPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.yellow);
  }
}
