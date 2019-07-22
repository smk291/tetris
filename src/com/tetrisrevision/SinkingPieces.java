package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

public class SinkingPieces {
  private ArrayList<ArrayList<Point>> sinkingPieces;

  SinkingPieces() {
    sinkingPieces = new ArrayList<>();
  }

  ArrayList<ArrayList<Point>> getSinkingPieces() {
    return sinkingPieces;
  }

  public void setSinkingPieces(ArrayList<ArrayList<Point>> sinkingPieces) {
    this.sinkingPieces = sinkingPieces;
  }

  void softDropSinkingPieces(PlayField field) {
    sinkingPieces.forEach(sinking -> Translater.translate(sinking, field, 0, 1));
  }

  public void removeSinkingPiece(ArrayList<Point> piece) {
    sinkingPieces.remove(piece);
  }
}

class SinkingPieces2d {
  private ArrayList<ArrayList<Cell>> sinking2d;

  SinkingPieces2d() {
    sinking2d = new ArrayList<>();
  }

  public ArrayList<ArrayList<Cell>> getPieces() {
    return sinking2d;
  }

  public void setPieces(ArrayList<ArrayList<Cell>> sinking) {
    this.sinking2d = sinking;
  }

  public void softDropPieces(Blocks2d blocks2d) {
    for (ArrayList<Cell> piece : sinking2d) {
      Translater2d.translate(piece, blocks2d, 0, -1);
    }
  }
}
