package com.tetrisrevision.gamemechanics;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.actions.Translater;
import com.tetrisrevision.constants.Constants;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.ActiveBlock;

import javax.swing.*;

public class LockDelay {
  private Timer movementLockDelay;
  private Timer rotationLockDelay;
  private Timer movementTimer;
  private Timer rotationTimer;
  public LockDelay(RunTetris rt) {
    setMovement(rt, Constants.movementLockDelay);
    setRotation(rt, Constants.rotationLockDelay);
  }

  private void setRotation(RunTetris rt, int ms) {
    movementLockDelay =
        new Timer(
            ms,
            e -> {
              Movement.dropSinkingPieces(rt);
            });

  } 
  private void setMovement(RunTetris rt, int ms) {

    
  }

  private void handleLockDelay(RunTetris rt, Timer t) {
    ActiveBlock tp = rt.getCurrentPiece();
    RowList playfield = rt.getPlayfield();

    movementLockDelay.restart();
    movementLockDelay.stop();

    boolean canDrop = Translater.translate(tp, playfield, 0, Constants.down, true);

    if (canDrop) return;

    movementLockDelay.setRepeats(false);
    movementLockDelay.start();
  }

  public void handleRotationLockDelay(RunTetris rt) {
    ActiveBlock tp = rt.getCurrentPiece();
    RowList playfield = rt.getPlayfield();

    boolean canDrop = Translater.translate(tp, playfield, 0, Constants.down, true);

    if (null != rotationTimer) {
      rotationTimer.restart();
      rotationTimer.stop();
    }

    if (canDrop) return;

    rotationTimer = new Timer(Constants.rotationLockDelay, e -> rt.addPieceToPlayfield(tp));
    rotationTimer.setRepeats(false);
    rotationTimer.start();
  }

  public void handleMovementLockDelay(RunTetris rt) {
    ActiveBlock tp = rt.getCurrentPiece();
    RowList playfield = rt.getPlayfield();

    boolean canDrop = Translater.translate(tp, playfield, 0, Constants.down, true);

    if (canDrop) {
      if (null != movementTimer && movementTimer.isRunning()) movementTimer.stop();

      return;
    }

    if (null == movementTimer || !movementTimer.isRunning()) {
      movementTimer = new Timer(Constants.movementLockDelay, e -> rt.addPieceToPlayfield(tp));
      movementTimer.setRepeats(false);
      movementTimer.start();
    }
  }
}
