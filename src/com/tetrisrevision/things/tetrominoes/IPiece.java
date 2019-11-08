package com.tetrisrevision.things.tetrominoes;

import com.tetrisrevision.helpers.RelativeCoords;

import java.awt.*;
import java.util.HashMap;

abstract class IPieceSequences {
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

public class IPiece extends Tetromino {
  private static final int[][][] offsets = {
    {RelativeCoords.l1, RelativeCoords.center, RelativeCoords.r1, RelativeCoords.r2},
    {
      RelativeCoords.r1u1, RelativeCoords.r1, RelativeCoords.r1d1, RelativeCoords.r1d2,
    },
    {
      RelativeCoords.l1d1, RelativeCoords.d1, RelativeCoords.r1d1, RelativeCoords.r2d1,
    },
    {RelativeCoords.u1, RelativeCoords.center, RelativeCoords.d1, RelativeCoords.d2}
  };

  private static HashMap<Integer, HashMap<Integer, int[][]>> kickData =
      new HashMap<>() {
        {
          put(
              0,
              new HashMap<>() {
                {
                  put(1, IPieceSequences.seqs[0]);
                  put(3, IPieceSequences.seqs[1]);
                }
              });
          put(
              1,
              new HashMap<>() {
                {
                  put(0, IPieceSequences.seqs[2]);
                  put(2, IPieceSequences.seqs[1]);
                }
              });
          put(
              2,
              new HashMap<>() {
                {
                  put(1, IPieceSequences.seqs[3]);
                  put(3, IPieceSequences.seqs[2]);
                }
              });
          put(
              3,
              new HashMap<>() {
                {
                  put(0, IPieceSequences.seqs[3]);
                  put(2, IPieceSequences.seqs[0]);
                }
              });
        }
      };

  IPiece() {
    super.setOffsets(offsets);
    super.setKickData(kickData);
    super.setColor(Color.CYAN);
  }
}
