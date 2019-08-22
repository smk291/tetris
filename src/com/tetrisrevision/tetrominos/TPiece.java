package com.tetrisrevision.tetrominos;

import java.awt.*;

class TPiece extends Tetromino {
  private static final int[][][] offsets = {
    {Coords.u1, Coords.l1, {0, 0}, Coords.r1}, // 0 up
    {Coords.u1, {0, 0}, Coords.r1, Coords.d1}, // 1 right
    {Coords.l1, {0, 0}, Coords.r1, Coords.d1}, // 2 down
    {Coords.u1, Coords.l1, {0, 0}, Coords.d1} // 3 left
  };

  TPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.magenta);
    super.setTPiece(true);
  }
}
