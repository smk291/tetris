package com.tetris.things;

import com.tetris.constants.RelativeCoords;

// Kick sequences taken from https://tetris.wiki/SRS
abstract public class KickSequences {
  static final int[][][] seqs = {
    {RelativeCoords.r1, RelativeCoords.r1d1, RelativeCoords.u2, RelativeCoords.r1u2},
    {RelativeCoords.l1, RelativeCoords.l1u1, RelativeCoords.d2, RelativeCoords.l1d2},
    {RelativeCoords.l1, RelativeCoords.l1d1, RelativeCoords.u2, RelativeCoords.l1u2},
    {RelativeCoords.r1, RelativeCoords.r1u1, RelativeCoords.d2, RelativeCoords.r1d2}
  };
}
