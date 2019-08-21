package com.tetrisrevision.tetrominos;

import com.tetrisrevision.TetrisConstants;
import java.awt.*;

class TPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{0, TetrisConstants.up()}, {TetrisConstants.left(), 0}, {0, 0}, {TetrisConstants.right(), 0}}, // 0 up
    {{0, TetrisConstants.up()}, {0, 0}, {TetrisConstants.right(), 0}, {0, TetrisConstants.down()}}, // 1 right
    {{TetrisConstants.left(), 0}, {0, 0}, {TetrisConstants.right(), 0}, {0, TetrisConstants.down()}}, // 2 down
    {{0, TetrisConstants.up()}, {TetrisConstants.left(), 0}, {0, 0}, {0, TetrisConstants.down()}} // 3 left
  };

  TPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.magenta);
    super.setTPiece(true);
  }
}
