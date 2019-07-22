package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

public interface GUI {
  void field(PlayField field);

  char cell(boolean isFull);

  void field(TetrisPiece piece, SinkingPieces sinkingPieces, PlayField field);
}
