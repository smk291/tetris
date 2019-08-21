package com.tetrisrevision.tetrominos;

import com.tetrisrevision.TetrisConstants;
import java.awt.*;

class ZPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{TetrisConstants.left(), TetrisConstants.up()}, {0, TetrisConstants.up()}, {0, 0}, {TetrisConstants.right(), 0}},
    {{TetrisConstants.right(), TetrisConstants.up()}, {0, 0}, {TetrisConstants.right(), 0}, {0, TetrisConstants.down()}},
    {{TetrisConstants.left(), 0}, {0, 0}, {0, TetrisConstants.down()}, {TetrisConstants.right(), TetrisConstants.down()}},
    {{0, TetrisConstants.up()}, {TetrisConstants.left(), 0}, {0, 0}, {TetrisConstants.left(), TetrisConstants.down()}}
  };

  ZPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.red);
  }
}
