package com.tetris.game.things.tetrominoes;

import com.tetris.game.constants.RelativeCoords;
import com.tetris.game.things.Tetromino;

import java.awt.*;

public class OBlock extends Tetromino {
  private static final int[][][] offsets = {{RelativeCoords.u1, RelativeCoords.r1u1, RelativeCoords.center, RelativeCoords.r1}};

  public OBlock() {
    super.setOffsets(offsets);
    super.setColor(Color.yellow);
  }
}
