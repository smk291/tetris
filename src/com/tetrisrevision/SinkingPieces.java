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
}
