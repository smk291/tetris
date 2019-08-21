package com.tetrisrevision.tetrominos;

import com.tetrisrevision.TetrisConstants;
import java.awt.*;

class SPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{0, TetrisConstants.up()}, {TetrisConstants.right(), TetrisConstants.up()}, {TetrisConstants.left(), 0}, {0, 0}},
    {{0, TetrisConstants.up()}, {0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.right(), TetrisConstants.down()}},
    {{0, 0}, {TetrisConstants.right(), 0}, {TetrisConstants.left(), TetrisConstants.down()}, {0, TetrisConstants.down()}},
    {{TetrisConstants.left(), TetrisConstants.up()}, {TetrisConstants.left(), 0}, {0, 0}, {0, TetrisConstants.down()}}
  };

  SPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.GREEN);
  }
}
