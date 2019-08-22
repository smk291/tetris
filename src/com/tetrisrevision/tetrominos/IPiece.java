package com.tetrisrevision.tetrominos;

import java.awt.*;
import java.util.HashMap;

abstract class IPieceSequences {
  static final int[][][] seqs = {
    {
      Coords.l2, Coords.r1, Coords.l2d1, Coords.r1u2,
    },
    {Coords.l1, Coords.r2, Coords.l1u2, Coords.r2d1},
    {
      Coords.r2, Coords.l1, Coords.r2u1, Coords.l1d2,
    },
    {Coords.r1, Coords.l2, Coords.r1d2, Coords.l2u1}
  };
}

class IPiece extends Tetromino {
  private static final int[][][] offsets = {
    {Coords.l1, {0, 0}, Coords.r1, Coords.r2},
    {
      Coords.r1u1, Coords.r1, Coords.r1d1, Coords.r1d2,
    },
    {
      Coords.l1d1, Coords.d1, Coords.r1d1, Coords.r2d1,
    },
    {Coords.u1, {0, 0}, Coords.d1, Coords.u2}
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
