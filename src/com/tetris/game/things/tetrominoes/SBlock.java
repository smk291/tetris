package com.tetris.game.things.tetrominoes;

import com.tetris.game.constants.RelativeCoords;
import com.tetris.game.things.Tetromino;

import java.awt.*;

public class SBlock extends Tetromino {
  private static final int[][][] offsets = {
    {RelativeCoords.u1, RelativeCoords.r1u1, RelativeCoords.l1, RelativeCoords.center},
    {RelativeCoords.u1, RelativeCoords.center, RelativeCoords.r1, RelativeCoords.r1d1},
    {RelativeCoords.center, RelativeCoords.r1, RelativeCoords.l1d1, RelativeCoords.d1},
    {RelativeCoords.l1u1, RelativeCoords.l1, RelativeCoords.center, RelativeCoords.d1}
  };

  public SBlock() {
    super.setOffsets(offsets);
    super.setColor(Color.GREEN);
  }
}
