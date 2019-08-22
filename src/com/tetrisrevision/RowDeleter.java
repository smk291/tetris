package com.tetrisrevision;

import java.util.ArrayList;

abstract class RowDeleter {
  static ArrayList<Integer> apply(RowList blocksAdded, TetrisPiece piece, RowList board, GameRecordKeeping score, TetrisGUI gui) {
    int idx = board.getLowestFullRow(blocksAdded);              // Lowest Row to be deleted, below which no Row's `y`
                                                                // will need to shift
    ArrayList<Integer> sinkingPieceAnchors = new ArrayList<>(); // Row indices where one or more rows were deleted,
                                                                // i.e. where sinking pieces may be created
    if (idx == -1) {                                            // If no Rows will be deleted
      score.resetCombo();                                       // Thus Reset combo count
    } else {                                                    // Else Rows will be deleted
      score.incrCombo();                                        // Thus increment combo count

      int total = 0;                                            // Number of Rows removed
      int contig;                                               // Count continguous rows removed, reset on each loop
                                                                // iteration
      for (int i = idx; i < board.size(); i++) {                // Loop upward through Rows starting from `idx`
        contig = 0;                                             // Reset `contig`

        while (board.isFullRow(i)) {                            // If Row i is full
          board.remove(i);                                      // Remove Row i
          contig++;                                             // Increment `contig`
        }

        total += contig;                                        // Add `contig` to `total`

        score.computeAndAdd(contig, piece, board);              // Compute score of deletion and add to current score
        score.incrLinesCleared(contig, gui);                    // Increment line-cleared total

        Row r = board.get(i);

        if (r != null) {                                        // If there's still a Row at i
          r.setY(r.getY() - total);                             // Change its `y`, shifting it down by # of deleted Rows
          sinkingPieceAnchors.add(i);                           // `i` is Row where program will look for sinking piece
        }
      }
    }

    return sinkingPieceAnchors;                                 // Return sinking-piece anchors -- Row indices where program will look for
  }                                                             // sinking pieces
}
