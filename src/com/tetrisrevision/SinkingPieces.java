package com.tetrisrevision;

import java.util.ArrayList;

class SinkingPieces {
  private ArrayList<ArrayList<Block>> sinkingPieces2d;

  SinkingPieces() {
    sinkingPieces2d = new ArrayList<>();
  }

  ArrayList<ArrayList<Block>> getPieces() {
    return sinkingPieces2d;
  }
}
