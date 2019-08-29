package com.tetrisrevision;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class RunTetris {
  private TetrisPiece currentPiece = new TetrisPiece();
  private TetrominoQueue tetrominoQueue = new TetrominoQueue(currentPiece);
  private RowList playField = new RowList();
  private ArrayList<RowList> sinkingPieces = new ArrayList<>();
  private TetrisGUI tetrisGUI;
  private Timer movementTimer;
  private Timer rotationTimer;
  private GameRecordKeeping recordKeeping = new GameRecordKeeping();;

  RunTetris() {
    tetrominoQueue.resetCurrentPiece(currentPiece);
  }

  TetrisPiece getCurrentPiece() {
    return currentPiece;
  }

  RowList getPlayField() {
    return playField;
  }

  ArrayList<RowList> getSinkingPieces() {
    return sinkingPieces;
  }

  GameRecordKeeping getRecordKeeping() {
    return recordKeeping;
  }

  void setTetrisGUI(TetrisGUI t) {
    this.tetrisGUI = t;
  }

  void dropSinkingPieces() {
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
        RowDeleter.apply(sinkingPiece, currentPiece, playField, recordKeeping, tetrisGUI);

    sinkingPieces.remove(sinkingPiece);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingPieceFinder().findSinkingPieces(i, playField, sinkingPieces));
    }
  }

  private void addPieceToBoard(TetrisPiece piece) {
    playField.addRowList(piece.getBlocks());

    ArrayList<Integer> deletedRowIdx =
        RowDeleter.apply(piece.getBlocks(), piece, playField, recordKeeping, tetrisGUI);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingPieceFinder().findSinkingPieces(i, playField, sinkingPieces));
    }

    tetrominoQueue.resetCurrentPiece(piece);

    if (!PlacementTester.cellsCanBeOccupied(piece, playField)) {
      tetrisGUI.endGame();
    }
  }

  void dropCurrentPiece() {
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

  void keyboardInput(KeyEvent e, boolean shift) {
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

  TetrominoQueue getTetrominoQueue() {
    return tetrominoQueue;
  }
}
