package com.tetrisrevision.things.tetrominos;

import com.tetrisrevision.helpers.Coords;

import java.awt.*;

class JPiece extends Tetromino {
  private static final int[][][] offsets = {
    {Coords.l1u1, Coords.l1, {0, 0}, Coords.r1},
    {Coords.d1, Coords.r1u1, {0, 0}, Coords.u1},
    {Coords.l1, {0, 0}, Coords.r1, Coords.r1d1},
    {Coords.d1, {0, 0}, Coords.l1d1, Coords.u1}
  };

  JPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.blue);
  }
}
