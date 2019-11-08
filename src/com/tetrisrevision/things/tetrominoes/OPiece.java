package com.tetrisrevision.things.tetrominoes;

import com.tetrisrevision.helpers.RelativeCoords;

import java.awt.*;

public class OPiece extends Tetromino {
  private static final int[][][] offsets = {{RelativeCoords.u1, RelativeCoords.r1u1, RelativeCoords.center, RelativeCoords.r1}};

  public OPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.yellow);
  }
}
