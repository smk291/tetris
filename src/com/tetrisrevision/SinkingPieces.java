package com.tetrisrevision;

import java.util.ArrayList;

class SinkingPieces {
  private ArrayList<ArrayList<Cell>> sinkingPieces2d;

  SinkingPieces() {
    sinkingPieces2d = new ArrayList<>();
  }

  ArrayList<ArrayList<Cell>> getPieces() {
    return sinkingPieces2d;
  }

  void softDropPieces(Blocks2d field) {
    sinkingPieces2d.forEach(sinking -> Translater.translate(sinking, field, 1));
  }
}
