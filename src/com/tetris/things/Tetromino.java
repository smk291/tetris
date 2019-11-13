package com.tetris.things;

import java.awt.*;
import java.util.HashMap;

public class Tetromino {
  private int[][][] offsets;
  private Color color;
  private boolean tPiece = false;

  // @formatter:off
  private HashMap<Integer, HashMap<Integer, int[][]>> kickData =
      new HashMap<>() {
        {
          put(0, new HashMap<>() {{ put(1, KickSequences.seqs[1]); put(3, KickSequences.seqs[3]); }});
          put(1, new HashMap<>() {{ put(0, KickSequences.seqs[0]); put(2, KickSequences.seqs[0]); }});
          put(2, new HashMap<>() {{ put(1, KickSequences.seqs[1]); put(3, KickSequences.seqs[3]); }});
          put(3, new HashMap<>() {{ put(0, KickSequences.seqs[2]); put(2, KickSequences.seqs[2]); }});
        }
      };
  // @formatter:on

  public int[][][] getOffsets() {
    return this.offsets;
  }

  public void setOffsets(int[][][] offsets) {
    this.offsets = offsets;
  }

  public HashMap<Integer, HashMap<Integer, int[][]>> getKickData() {
    return kickData;
  }

  public void setKickData(HashMap<Integer, HashMap<Integer, int[][]>> kickData) {
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
