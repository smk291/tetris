package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

public class ModifyPlayField {
  public AddAndRemove addAndRemove;
  RowDeleter rowDeleter;

  ModifyPlayField(
      TetrisPiece falling, ArrayList<ArrayList<Point>> sinkingPieces, PlayField playField) {

    addAndRemove = new AddAndRemove(playField, falling, sinkingPieces);
    rowDeleter = new RowDeleter(playField);
  }
}

class RowDeleter {
  private PlayField playField;

  RowDeleter(PlayField playField) {
    this.playField = playField;
  }

  int apply(ArrayList<Point> testRows) {
    return apply(testRows.toArray(new Point[0]));
  }

  int apply(Point[] points) {
    int startAt = -1;

    for (Point c : points) {
      int row = (int) c.getY();

      if (playField.rowIsFull(row) && row > startAt) {
        startAt = row;
      }
    }

    int rowIdxForFindingFloatingPieces = startAt;

    if (startAt > -1) {
      int shift = 0;

      while (!playField.rowIsEmpty(startAt) && (startAt + shift) >= 0) {
        while (playField.rowIsFull(startAt + shift)) shift--;

        playField.copyRow(startAt + shift, startAt);

        startAt--;
      }

      for (int i = startAt, halt = startAt + shift; i >= 0 && i > halt; i--) {
        playField.emptyRow(i);
      }
    }

    return rowIdxForFindingFloatingPieces;
  }
}
