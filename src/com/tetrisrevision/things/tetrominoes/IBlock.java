package com.tetrisrevision.things.tetrominoes;

import com.tetrisrevision.constants.RelativeCoords;
import com.tetrisrevision.things.Tetromino;

import java.awt.*;
import java.util.HashMap;

public class IBlock extends Tetromino {
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
                  put(1, IPieceOffsets.seqs[0]);
                  put(3, IPieceOffsets.seqs[1]);
                }
              });
          put(
              1,
              new HashMap<>() {
                {
                  put(0, IPieceOffsets.seqs[2]);
                  put(2, IPieceOffsets.seqs[1]);
                }
              });
          put(
              2,
              new HashMap<>() {
                {
                  put(1, IPieceOffsets.seqs[3]);
                  put(3, IPieceOffsets.seqs[2]);
                }
              });
          put(
              3,
              new HashMap<>() {
                {
                  put(0, IPieceOffsets.seqs[3]);
                  put(2, IPieceOffsets.seqs[0]);
                }
              });
        }
      };

  public IBlock() {
    super.setOffsets(offsets);
    super.setKickData(kickData);
    super.setColor(Color.CYAN);
  }
}
