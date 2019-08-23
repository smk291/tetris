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

    System.out.println("lowest and highest full: " + lowestFullRow + ", " + highestFullRow);

    ArrayList<Integer> sinkingPieceAnchors = new ArrayList<>();

    if (lowestFullRow == -1) {
      score.resetCombo();
    } else {
      score.incrCombo();

      for (int i = lowestFullRow; board.size() > 0 && i < board.size() && i <= highestFullRow; i++) {
        int contigDeleted = 0;

        if (board.get().get(i).size() == Constants.width) {
          contigDeleted = board.deleteRows(i);
          sinkingPieceAnchors.add(i);
        }

        score.computeAndAdd(contigDeleted, piece, board);
        score.incrLinesCleared(contigDeleted, gui);

        if (board.get().size() == 0) {
          sinkingPieceAnchors.clear();

          break;
        }
      }
    }

    return sinkingPieceAnchors;
  }
}
