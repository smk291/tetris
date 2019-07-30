package com.tetrisrevision;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class RunTetris {
  private TetrisPiece currentPiece;
  private TetrominoQueue tetrominoQueue;
  private PlayField playField;
  private SinkingPieces sinkingPieces;
  private TetrisGUI tetrisGUI;
  private Timer movementTimer;
  private Timer rotationTimer;
  private GameRecordKeeping recordKeeping;

  RunTetris(int width, int height) {
    tetrominoQueue = new TetrominoQueue();
    currentPiece = new TetrisPiece();
    playField = new PlayField(width, height);
    sinkingPieces = new SinkingPieces();
    tetrominoQueue.resetCurrentPiece(currentPiece);
    recordKeeping = new GameRecordKeeping();
  }

  TetrisPiece getCurrentPiece() {
    return currentPiece;
  }

  PlayField getPlayField() {
    return playField;
  }

  SinkingPieces getSinkingPieces() {
    return sinkingPieces;
  }

  GameRecordKeeping getRecordKeeping() {
    return recordKeeping;
  }

  void setTetrisGUI(TetrisGUI t) {
    this.tetrisGUI = t;
  }

  void dropSinkingPieces() {
    for (int i = 0;
        sinkingPieces.getPieces().size() > 0 && i < sinkingPieces.getPieces().size();
        i++) {
      ArrayList<Block> sinkingPiece = sinkingPieces.getPieces().get(i);

      boolean canSink = Translater.translate(sinkingPiece, playField, 1);

      if (!canSink) {
        addSinkingPieceToBoard(sinkingPiece);

        i--;

        tetrisGUI.getBoardCompositor().repaint();
      }

      tetrisGUI.getBoardCompositor().repaint();
    }
  }

  private void addSinkingPieceToBoard(ArrayList<Block> sinkingPiece) {
    playField.set(sinkingPiece);

    int deletedRowIdx =
        RowDeleter.apply(sinkingPiece, currentPiece, playField, recordKeeping, tetrisGUI);

    sinkingPieces.getPieces().remove(sinkingPiece);

    if (deletedRowIdx > 0) new SinkingPieceFinder().find(deletedRowIdx, playField, sinkingPieces);
  }

  private void addPieceToBoard(TetrisPiece piece) {
    if (movementTimer != null) movementTimer.stop();
    if (rotationTimer != null) rotationTimer.stop();
    playField.set(piece);

    int deletedRowIdx = RowDeleter.apply(piece, playField, recordKeeping, tetrisGUI);

    if (deletedRowIdx > 0) new SinkingPieceFinder().find(deletedRowIdx, playField, sinkingPieces);

    tetrominoQueue.resetCurrentPiece(piece);

    if (!PlacementTester.cellsCanBeOccupied(piece, playField)) tetrisGUI.endGame();
  }

  void dropCurrentPiece() {
    translatePiece(0, 1);
  }

  private void translatePiece(int x, int y) {

    if (Translater.translate(currentPiece, playField, x, y, false)) {
      tetrisGUI.getBoardCompositor().repaint();

      currentPiece.gettSpinTracker().reset();
    } else if (y == 1) {
      handleMovementTimer();
    }
  }

  private void handleMovementTimer() {
    boolean canDrop = Translater.translate(currentPiece, playField, 0, 1, true);

    if (canDrop) {
      if (null != movementTimer && movementTimer.isRunning()) movementTimer.stop();

      return;
    }

    if (null == movementTimer || !movementTimer.isRunning()) {
      movementTimer = new Timer(500, e -> addPieceToBoard(currentPiece));
      movementTimer.setRepeats(false);
      movementTimer.start();
    }
  }

  private void rotate(int incr) {
    boolean canRotate = Rotator.apply(incr, currentPiece, playField);

    if (canRotate) tetrisGUI.getBoardCompositor().repaint();

    handleRotationTimer();
  }

  private void handleRotationTimer() {
    boolean canDrop = Translater.translate(currentPiece, playField, 0, 1, true);

    if (null != rotationTimer) {
      rotationTimer.restart();
      rotationTimer.stop();
    }

    if (canDrop) return;

    rotationTimer = new Timer(500, e -> addPieceToBoard(currentPiece));
    rotationTimer.setRepeats(false);
    rotationTimer.start();
  }

  void keyboardInput(KeyEvent e, boolean shift) {
    if (shift) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          rotate(-1);
          break;
        case KeyEvent.VK_RIGHT:
          rotate(1);
          break;
        case KeyEvent.VK_DOWN:
          while (sinkingPieces.getPieces().size() > 0) dropSinkingPieces();

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
          translatePiece(-1, 0);
          break;
        case KeyEvent.VK_RIGHT:
          translatePiece(1, 0);
          break;
        case KeyEvent.VK_DOWN:
          translatePiece(0, 1);
          recordKeeping.softDrop();

          break;
//        case KeyEvent.VK_UP:
//          translatePiece(0, -1);
//          break;
        default:
          InputTests.accept(Character.toString(e.getKeyChar()), currentPiece, playField);
          break;
      }
    }
    tetrisGUI.getBoardCompositor().repaint();
  }

  public TetrominoQueue getTetrominoQueue() {
    return tetrominoQueue;
  }
}
