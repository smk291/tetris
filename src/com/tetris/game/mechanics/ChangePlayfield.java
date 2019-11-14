package com.tetris.game.mechanics;

import com.tetris.game.RunTetris;
import com.tetris.game.actions.PlacementTester;
import com.tetris.game.actions.ClearRows;
import com.tetris.game.actions.SinkingBlockFinder;
import com.tetris.game.constants.Constants;
import com.tetris.game.recordkeeping.GameRecordKeeping;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;

import javax.swing.*;
import java.util.ArrayList;

public class ChangePlayfield {
  public static void addSinkingBlockToBoard(RunTetris rt, RowList sinkingBlock) {
    RowList playfield = rt.getPlayfield();
    ActiveBlock currentBlock = rt.getActiveBlock();
    GameRecordKeeping records = rt.getRecordKeeping();
    ArrayList<RowList> sinkingBlocks = rt.getSinkingBlocks();

    playfield.addRowList(sinkingBlock);

    ArrayList<Integer> deletedRowIdx =
        ClearRows.apply(sinkingBlock, currentBlock, playfield, records);

    sinkingBlocks.remove(sinkingBlock);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingBlockFinder().findSinkingBlocks(i, playfield, sinkingBlocks));
    }
  }

  public static void addActiveBlockToPlayfield(RunTetris rt, ActiveBlock block) {
    RowList playfield = rt.getPlayfield();
    GameRecordKeeping records = rt.getRecordKeeping();

    playfield.addRowList(block.getSquares());

    ArrayList<Integer> deletedRowIdx = ClearRows.apply(block.getSquares(), block, playfield, records);

    rt.getRecordKeeping().incrLinesCleared(deletedRowIdx.size());

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingBlockFinder().findSinkingBlocks(i, playfield, rt.getSinkingBlocks()));
    }

    // If blocks other than current block can be added, then this needs to change
    rt.getTetrominoQueue().resetCurrentBlock(block);
    rt.setActiveBlockTimer();

    if (!PlacementTester.cellsCanBeOccupied(block, playfield)) {
      rt.endGame();
    }
  }
}
