package com.tetrisrevision.tetrominos;

import java.awt.*;

class SPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{0, -1}, {1, -1}, {-1, 0}, {0, 0}},
    {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
    {{0, 0}, {1, 0}, {-1, 1}, {0, 1}},
    {{-1, -1}, {-1, 0}, {0, 0}, {0, 1}}
  };

  SPiece() {
    super.setOffsets(offsets);
    super.setColor(new Color(0, 158, 96));
  }
}
