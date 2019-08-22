package com.tetrisrevision;

import java.util.ArrayList;

abstract class RowDeleter {
  static ArrayList<Integer> apply(
      RowList blocksAdded,
      TetrisPiece piece,
      RowList board,
      GameRecordKeeping score,
      TetrisGUI gui) {
    int idx = board.getLowestFullRow(blocksAdded);

    ArrayList<Integer> sinkingPieceAnchors = new ArrayList<>();

    if (idx == -1) {
      score.resetCombo();
    } else {
      score.incrCombo();

      int total = 0;
      int contig;

      for (int i = idx; i < board.size(); i++) {
        contig = 0;
        ArrayList<Row> delete = new ArrayList<>();

        int j = i;
        while (board.isFullRow(j)) {
          Row r = board.get(j);

          if (r != null) {
            delete.add(r);
          }

          j++;
        }

        board.get().removeAll(delete);

        total += contig;
        score.computeAndAdd(contig, piece, board);
        score.incrLinesCleared(contig, gui);

        if (board.get().size() == 0) {
          sinkingPieceAnchors.clear();

          break;
        } else {
          Row r = board.get(i);

          if (r != null) {
            r.setY(r.getY() - total);

            sinkingPieceAnchors.add(i);
          }
        }
      }
    }

    return sinkingPieceAnchors;
  }
}
