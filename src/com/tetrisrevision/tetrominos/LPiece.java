package com.tetrisrevision.tetrominos;

import com.tetrisrevision.TetrisConstants;
import java.awt.*;

class LPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{TetrisConstants.right(), TetrisConstants.up()}, {TetrisConstants.left(), 0}, {0, 0}, {TetrisConstants.right(), 0}},
    {{0, TetrisConstants.up()}, {0, 0}, {0, TetrisConstants.down()}, {TetrisConstants.right(), TetrisConstants.down()}},
    {{TetrisConstants.left(), 0}, {0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.left(), TetrisConstants.down()}},
    {{TetrisConstants.left(), TetrisConstants.up()}, {0, TetrisConstants.up()}, {0, 0}, {0, TetrisConstants.down()}}
  };

  LPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.orange);
  }
}
