package com.tetris.game.mechanics;

import com.tetris.game.RunTetris;
import com.tetris.game.actions.Rotator;
import com.tetris.game.actions.Translater;
import com.tetris.game.constants.Constants;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;

import javax.swing.*;
import java.util.ArrayList;

abstract public class Movement {
  public static void translateBlock(RunTetris rt, JFrame frame, int x, int y) {
    ActiveBlock tp = rt.getActiveBlock();
    RowList playfield = rt.getPlayfield();
    LockDelay ld = rt.getLockDelay();

    boolean canTranslate = Translater.translate(tp, playfield, x, y, false);

    if (canTranslate) {
      tp.gettSpinTracker().reset();
    } else if (y == Constants.down) {
      rt.addBlockToPlayfield(rt.getActiveBlock());
//      ld.handleMovementLockDelay(rt);
    }
  }

  public static void rotate(RunTetris rt, JFrame frame, int incr) {
    ActiveBlock tp = rt.getActiveBlock();
    RowList playfield = rt.getPlayfield();
    LockDelay ld = rt.getLockDelay();

    int canRotate = Rotator.apply(incr, tp, playfield);

    if (canRotate > -1) {
      return;
    }

    ld.handleRotationLockDelay(rt);
  }


  public static void dropSinkingBlocks(RunTetris rt) {
    ArrayList<RowList> sinkingBlocks = rt.getSinkingBlocks();
    RowList playfield = rt.getPlayfield();

    for (int i = 0; !sinkingBlocks.isEmpty() && i < sinkingBlocks.size(); i++) {
      RowList sinkingBlock = sinkingBlocks.get(i);

      boolean canSink = Translater.translate(sinkingBlock, playfield, Constants.down);

      if (!canSink) {
        rt.addSinkingBlockToPlayfield(sinkingBlock);

        i--;
      }
    }
  }

}
