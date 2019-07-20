package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

public interface GUI {
  void draw(PlayField field);

  char drawCell(boolean isFull);

  void drawBoardIncludingPiece(TetrisPiece piece, ArrayList<ArrayList<Point>> sinkingPieces, PlayField field);
}
