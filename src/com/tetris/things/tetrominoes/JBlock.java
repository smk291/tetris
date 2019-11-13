package com.tetris.things.tetrominoes;

import com.tetris.constants.RelativeCoords;
import com.tetris.things.Tetromino;

import java.awt.*;

public class JBlock extends Tetromino {
  private static final int[][][] offsets = {
    {RelativeCoords.l1u1, RelativeCoords.l1, RelativeCoords.center, RelativeCoords.r1},
    {RelativeCoords.d1, RelativeCoords.r1u1, RelativeCoords.center, RelativeCoords.u1},
    {RelativeCoords.l1, RelativeCoords.center, RelativeCoords.r1, RelativeCoords.r1d1},
    {RelativeCoords.d1, RelativeCoords.center, RelativeCoords.l1d1, RelativeCoords.u1}
  };

  public JBlock() {
    super.setOffsets(offsets);
    super.setColor(Color.blue);
  }
}