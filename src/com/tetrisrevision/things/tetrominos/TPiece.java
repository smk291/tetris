package com.tetrisrevision.things.tetrominos;

import com.tetrisrevision.helpers.RelativeCoords;

import java.awt.*;

class TPiece extends Tetromino {
  private static final int[][][] offsets = {
    {RelativeCoords.u1, RelativeCoords.l1, RelativeCoords.center, RelativeCoords.r1}, // 0 up
    {RelativeCoords.u1, RelativeCoords.center, RelativeCoords.r1, RelativeCoords.d1}, // 1 right
    {RelativeCoords.l1, RelativeCoords.center, RelativeCoords.r1, RelativeCoords.d1}, // 2 down
    {RelativeCoords.u1, RelativeCoords.l1, RelativeCoords.center, RelativeCoords.d1} // 3 left
  };

  TPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.magenta);
    super.setTPiece(true);
  }
}
