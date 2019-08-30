package com.tetrisrevision;

import com.tetrisrevision.actions.*;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.recordkeeping.GameRecordKeeping;
import com.tetrisrevision.testing.InputTests;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.TetrominoQueue;
import com.tetrisrevision.things.tetrominos.Tetromino;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class RunTetris {
  private TetrisPiece currentPiece = new TetrisPiece();
  private TetrominoQueue tetrominoQueue = new TetrominoQueue(currentPiece);
  private Tetromino holdPiece;
  private RowList playField = new RowList();
  private ArrayList<RowList> sinkingPieces = new ArrayList<>();
  private Timer movementTimer;
  private Timer rotationTimer;
  private GameRecordKeeping recordKeeping = new GameRecordKeeping();;

  public RunTetris() {
    tetrominoQueue.resetCurrentPiece(currentPiece);
  }

  public TetrisPiece getCurrentPiece() {
    return currentPiece;
  }

  public RowList getPlayField() {
    return playField;
  }

  public ArrayList<RowList> getSinkingPieces() {
    return sinkingPieces;
  }

  public GameRecordKeeping getRecordKeeping() {
    return recordKeeping;
  }

  public void dropSinkingPieces() {
    for (int i = 0; !sinkingPieces.isEmpty() && i < sinkingPieces.size(); i++) {
      RowList sinkingPiece = sinkingPieces.get(i);

      boolean canSink = Translater.translate(sinkingPiece, playField, Constants.down);

      if (!canSink) {
        addSinkingPieceToBoard(sinkingPiece);

        i--;
      }
    }
  }

  private void addSinkingPieceToBoard(RowList sinkingPiece) {
    playField.addRowList(sinkingPiece);

    ArrayList<Integer> deletedRowIdx =
        RowDeleter.apply(sinkingPiece, currentPiece, playField, recordKeeping);

    sinkingPieces.remove(sinkingPiece);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingPieceFinder().findSinkingPieces(i, playField, sinkingPieces));
    }
  }

  private void addPieceToBoard(TetrisPiece piece) {
    playField.addRowList(piece.getBlocks());

    ArrayList<Integer> deletedRowIdx =
        RowDeleter.apply(piece.getBlocks(), piece, playField, recordKeeping);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingPieceFinder().findSinkingPieces(i, playField, sinkingPieces));
    }

    tetrominoQueue.resetCurrentPiece(piece);

    if (!PlacementTester.cellsCanBeOccupied(piece, playField)) {
      // End game
    }
  }

  public void dropCurrentPiece() {
    translatePiece(0, Constants.down);
  }

  private void translatePiece(int x, int y) {
    boolean canTranslate = Translater.translate(currentPiece, playField, x, y, false);

    if (canTranslate) {
      currentPiece.gettSpinTracker().reset();
    } else if (y == Constants.down) {
      addPieceToBoard(currentPiece);
    }
  }

  private void handleMovementTimer() {
    boolean canDrop = Translater.translate(currentPiece, playField, 0, Constants.down, true);

    if (canDrop) {
      if (null != movementTimer && movementTimer.isRunning()) movementTimer.stop();

      return;
    }

    if (null == movementTimer || !movementTimer.isRunning()) {
      movementTimer = new Timer(Constants.timerDelay, e -> addPieceToBoard(currentPiece));
      movementTimer.setRepeats(false);
      movementTimer.start();
    }
  }

  private void rotate(int incr) {
    boolean canRotate = Rotator.apply(incr, currentPiece, playField);

    if (canRotate) {
      return;
    }

    handleRotationTimer();
  }

  private void handleRotationTimer() {
    boolean canDrop = Translater.translate(currentPiece, playField, 0, Constants.down, true);

    if (null != rotationTimer) {
      rotationTimer.restart();
      rotationTimer.stop();
    }

    if (canDrop) return;

    rotationTimer = new Timer(Constants.timerDelay, e -> addPieceToBoard(currentPiece));
    rotationTimer.setRepeats(false);
    rotationTimer.start();
  }

  public void keyboardInput(KeyEvent e, boolean shift) {
    if (e.getKeyCode() == KeyEvent.VK_Q) {
      if (holdPiece == null) {
        holdPiece = currentPiece.getTetromino();
        tetrominoQueue.resetCurrentPiece(currentPiece);
      } else {
        Tetromino tmp = holdPiece;
        holdPiece = currentPiece.getTetromino();
        currentPiece.reset(tmp);
      }
    }

    if (shift) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          rotate(Constants.counterClockwise);

          break;
        case KeyEvent.VK_RIGHT:
          rotate(Constants.clockwise);

          break;
        case KeyEvent.VK_DOWN:
          while (!sinkingPieces.isEmpty()) dropSinkingPieces();

          int rowsTraversed = Translater.hardDrop(currentPiece, playField);

          recordKeeping.hardDrop(rowsTraversed);

          addPieceToBoard(currentPiece);

          break;
        default:
          InputTests.accept(Character.toString(e.getKeyChar()), currentPiece, playField);

          break;
      }
    } else {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          translatePiece(Constants.left, 0);

          break;
        case KeyEvent.VK_RIGHT:
          translatePiece(Constants.right, 0);

          break;
        case KeyEvent.VK_DOWN:
          translatePiece(0, Constants.down);
          recordKeeping.softDrop();

          break;
           case KeyEvent.VK_UP:
             translatePiece(0, Constants.up);

             break;
        default:
          InputTests.accept(Character.toString(e.getKeyChar()), currentPiece, playField);

          break;
      }
    }
  }

  public TetrominoQueue getTetrominoQueue() {
    return tetrominoQueue;
  }

  public @Nullable Tetromino getHoldPiece() {
    return holdPiece;
  }

  public void setHoldPiece(Tetromino holdPiece) {
    this.holdPiece = holdPiece;
  }
}
