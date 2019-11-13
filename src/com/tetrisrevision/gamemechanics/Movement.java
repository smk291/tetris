package com.tetrisrevision.gamemechanics;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.actions.Rotator;
import com.tetrisrevision.actions.Translater;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;

import javax.swing.*;
import java.util.ArrayList;

abstract public class Movement {
  public static void translatePiece(RunTetris rt, JFrame frame, int x, int y) {
    TetrisPiece tp = rt.getCurrentPiece();
    RowList playfield = rt.getPlayfield();
    LockDelay ld = rt.getLockDelay();

    boolean canTranslate = Translater.translate(tp, playfield, x, y, false);

    if (canTranslate) {
      tp.gettSpinTracker().reset();
    } else if (y == Constants.down) {
      rt.addPieceToPlayfield(rt.getCurrentPiece());
//      ld.handleMovementLockDelay(rt);
    }
  }

  public static void rotate(RunTetris rt, JFrame frame, int incr) {
    TetrisPiece tp = rt.getCurrentPiece();
    RowList playfield = rt.getPlayfield();
    LockDelay ld = rt.getLockDelay();

    int canRotate = Rotator.apply(incr, tp, playfield);

    if (canRotate > -1) {
      return;
    }

    ld.handleRotationLockDelay(rt);
  }


  public static void dropSinkingPieces(RunTetris rt) {
    ArrayList<RowList> sinkingPieces = rt.getSinkingPieces();
    RowList playfield = rt.getPlayfield();

    for (int i = 0; !sinkingPieces.isEmpty() && i < sinkingPieces.size(); i++) {
      RowList sinkingPiece = sinkingPieces.get(i);

      boolean canSink = Translater.translate(sinkingPiece, playfield, Constants.down);

      if (!canSink) {
        rt.addSinkingPieceToBoard(sinkingPiece);

        i--;
      }
    }
  }

}
