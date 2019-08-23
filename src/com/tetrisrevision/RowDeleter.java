package com.tetrisrevision;

import java.util.ArrayList;

abstract class RowDeleter {

  static ArrayList<Integer> apply(
      RowList blocksAdded,
      TetrisPiece piece,
      RowList board,
      GameRecordKeeping score,
      TetrisGUI gui) {
    int lowestFullRow = board.getLowestFullRow(blocksAdded);
    int highestFullRow = board.getHighestFullRow(blocksAdded);

//    System.out.println("lowest, highest: " + lowestFullRow + ", " + highestFullRow);

    ArrayList<Integer> sinkingPieceAnchors = new ArrayList<>();

    if (lowestFullRow == -1) {
      score.resetCombo();
    } else {
      score.incrCombo();

      for (int i = blocksAdded.getYMinIdx(), total = 0; board.size() > 0 && i < board.size() && i <= highestFullRow; i++) {
        int contigDeleted = 0;

        if (board.get().get(i).size() == Constants.width) {
          contigDeleted = board.deleteContiguous(i, total);
          sinkingPieceAnchors.add(i);
        }

        total += contigDeleted;

        score.computeAndAdd(contigDeleted, piece, board);
        score.incrLinesCleared(contigDeleted, gui);

        if (board.get().size() == 0) {
          sinkingPieceAnchors.clear();
        }
      }
    }

    return sinkingPieceAnchors;
  }
}
