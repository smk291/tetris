package com.tetrisrevision.things.tetrominos;

import com.tetrisrevision.helpers.Coords;

import java.awt.*;
import java.util.HashMap;

// Kick sequences taken from https://tetris.wiki/SRS
abstract class Sequences {
  static final int[][][] seqs = {
    {Coords.r1, Coords.r1d1, Coords.u2, Coords.r1u2},
    {Coords.l1, Coords.l1u1, Coords.d2, Coords.l1d2},
    {Coords.l1, Coords.l1d1, Coords.u2, Coords.l1u2},
    {Coords.r1, Coords.r1u1, Coords.d2, Coords.r1d2}
  };
}

public class Tetromino {
  private int[][][] offsets;
  private Color color;
  private boolean tPiece = false;

  private HashMap<Integer, HashMap<Integer, int[][]>> kickData =
      new HashMap<>() {
        {
          put(
              0,
              new HashMap<>() {
                {
                  put(1, Sequences.seqs[1]);
                  put(3, Sequences.seqs[3]);
                }
              });
          put(
              1,
              new HashMap<>() {
                {
                  put(0, Sequences.seqs[0]);
                  put(2, Sequences.seqs[0]);
                }
              });
          put(
              2,
              new HashMap<>() {
                {
                  put(1, Sequences.seqs[1]);
                  put(3, Sequences.seqs[3]);
                }
              });
          put(
              3,
              new HashMap<>() {
                {
                  put(0, Sequences.seqs[2]);
                  put(2, Sequences.seqs[2]);
                }
              });
        }
      };

  public int[][][] getOffsets() {
    return this.offsets;
  }

  void setOffsets(int[][][] offsets) {
    this.offsets = offsets;
  }

  public HashMap<Integer, HashMap<Integer, int[][]>> getKickData() {
    return kickData;
  }

  void setKickData(HashMap<Integer, HashMap<Integer, int[][]>> kickData) {
    this.kickData = kickData;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public boolean isTPiece() {
    return this.tPiece;
  }

  public void setTPiece(boolean b) {
    this.tPiece = b;
  }
}
