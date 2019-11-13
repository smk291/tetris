package com.tetrisrevision.things.tetrominoes;

import com.tetrisrevision.constants.RelativeCoords;
import com.tetrisrevision.things.Tetromino;

import java.awt.*;

public class OBlock extends Tetromino {
  private static final int[][][] offsets = {{RelativeCoords.u1, RelativeCoords.r1u1, RelativeCoords.center, RelativeCoords.r1}};

  public OBlock() {
    super.setOffsets(offsets);
    super.setColor(Color.yellow);
  }
}
