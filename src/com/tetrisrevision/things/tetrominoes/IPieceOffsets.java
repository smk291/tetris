package com.tetrisrevision.things.tetrominoes;

import com.tetrisrevision.constants.RelativeCoords;

abstract public class IPieceOffsets {
  static final int[][][] seqs = {
    {
      RelativeCoords.l2, RelativeCoords.r1, RelativeCoords.l2d1, RelativeCoords.r1u2,
    },
    {RelativeCoords.l1, RelativeCoords.r2, RelativeCoords.l1u2, RelativeCoords.r2d1},
    {
      RelativeCoords.r2, RelativeCoords.l1, RelativeCoords.r2u1, RelativeCoords.l1d2,
    },
    {RelativeCoords.r1, RelativeCoords.l2, RelativeCoords.r1d2, RelativeCoords.l2u1}
  };
}
