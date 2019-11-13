package com.tetrisrevision.things.tetrominoes;

import com.tetrisrevision.constants.RelativeCoords;
import com.tetrisrevision.things.Tetromino;

import java.awt.*;

public class LBlock extends Tetromino {
  private static final int[][][] offsets = {
    {RelativeCoords.r1u1, RelativeCoords.l1, RelativeCoords.center, RelativeCoords.r1},
    {RelativeCoords.u1, RelativeCoords.center, RelativeCoords.d1, RelativeCoords.r1d1},
    {RelativeCoords.l1, RelativeCoords.center, RelativeCoords.r1, RelativeCoords.l1d1},
    {RelativeCoords.l1u1, RelativeCoords.u1, RelativeCoords.center, RelativeCoords.d1}
  };

  public LBlock() {
    super.setOffsets(offsets);
    super.setColor(Color.orange);
  }
}
