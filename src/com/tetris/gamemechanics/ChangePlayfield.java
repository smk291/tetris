package com.tetris.gamemechanics;

import com.tetris.RunTetris;
import com.tetris.actions.PlacementTester;
import com.tetris.actions.ClearRows;
import com.tetris.actions.SinkingBlockFinder;
import com.tetris.recordkeeping.GameRecordKeeping;
import com.tetris.things.RowList;
import com.tetris.things.ActiveBlock;

import java.util.ArrayList;

public class ChangePlayfield {
  public static void addSinkingPieceToBoard(RunTetris rt, RowList sinkingPiece) {
    RowList playfield = rt.getPlayfield();
    ActiveBlock currentBlock = rt.getCurrentPiece();
    GameRecordKeeping records = rt.getRecordKeeping();
    ArrayList<RowList> sinkingPieces = rt.getSinkingPieces();

    playfield.addRowList(sinkingPiece);

    ArrayList<Integer> deletedRowIdx =
        ClearRows.apply(sinkingPiece, currentBlock, playfield, records);

    sinkingPieces.remove(sinkingPiece);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingBlockFinder().findSinkingPieces(i, playfield, sinkingPieces));
    }
  }

  public static void addPieceToPlayfield(RunTetris rt, ActiveBlock block) {
    RowList playfield = rt.getPlayfield();
    GameRecordKeeping records = rt.getRecordKeeping();

    playfield.addRowList(block.getSquares());

    ArrayList<Integer> deletedRowIdx =
        ClearRows.apply(block.getSquares(), block, playfield, records);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingBlockFinder().findSinkingPieces(i, playfield, rt.getSinkingPieces()));
    }

    // If blocks other than current block can be added, then this needs to change
    rt.getTetrominoQueue().resetCurrentPiece(block);

    if (!PlacementTester.cellsCanBeOccupied(block, playfield)) {
      //

      return;
    }
  }
}
