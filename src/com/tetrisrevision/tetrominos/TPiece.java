package com.tetrisrevision.tetrominos;

import com.tetrisrevision.Blocks2d;

import java.awt.*;

class TPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{0, -1}, {-1, 0}, {0, 0}, {1, 0}}, // 0 up
    {{0, -1}, {0, 0}, {1, 0}, {0, 1}},  // 1 right
    {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},  // 2 down
    {{0, -1}, {-1, 0}, {0, 0}, {0, 1}}  // 3 left
  };


  TPiece() {
    super.setOffsets(offsets);
    super.setColor(new Color(147, 112, 219));
    super.setTPiece(true);
  }
}
