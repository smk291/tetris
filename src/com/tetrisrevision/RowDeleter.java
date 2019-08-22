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

      for (int i = idx; i < board.size(); i++) {
        int contig = 0;
        ArrayList<Row> delete = new ArrayList<>();

        int j = i;

        while (board.isFullRow(j)) {
          board.getRow(j).ifPresent(delete::add);

          contig++;
          j++;
        }

        total += contig;

        board.get().removeAll(delete);
        score.computeAndAdd(contig, piece, board);
        score.incrLinesCleared(contig, gui);

        if (board.get().size() == 0) {
          sinkingPieceAnchors.clear();

          break;
        }

        Row r = board.get(i);

        if (r != null) {
          r.setY(r.getY() - total);

          sinkingPieceAnchors.add(i);
        }
      }
    }

    return sinkingPieceAnchors;
  }
}
