package com.tetrisrevision.tetrominos;

import java.awt.*;

class JPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{-1, -1}, {-1, 0}, {0, 0}, {1, 0}},
    {{0, 1}, {1, -1}, {0, 0}, {0, -1}},
    {{-1, 0}, {0, 0}, {1, 0}, {1, 1}},
    {{0, 1}, {0, 0}, {-1, 1}, {0, -1}}
  };

  JPiece() {
    super.setOffsets(offsets);
    super.setColor(new Color(31, 117, 254));
  }
}
