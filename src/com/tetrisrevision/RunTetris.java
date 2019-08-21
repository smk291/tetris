package com.tetrisrevision;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class RunTetris {
  private TetrisPiece currentPiece;
  private TetrominoQueue tetrominoQueue;
  private RowList playField;
  private ArrayList<RowList> sinkingPieces;
  private TetrisGUI tetrisGUI;
  private Timer movementTimer;
  private Timer rotationTimer;
  private GameRecordKeeping recordKeeping;

  RunTetris(int width, int height) {
    tetrominoQueue = new TetrominoQueue();
    currentPiece = new TetrisPiece();
    playField = new RowList(width, height);
    sinkingPieces = new ArrayList<>();
    tetrominoQueue.resetCurrentPiece(currentPiece);
    recordKeeping = new GameRecordKeeping();
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

      boolean canSink = Translater.translate(sinkingPiece, playField, TetrisConstants.down());

      if (!canSink) {
        addSinkingPieceToBoard(sinkingPiece);

        i--;

        tetrisGUI.getBoardCompositor().repaint();
      }

      tetrisGUI.getBoardCompositor().repaint();
    }
  }

  private void addSinkingPieceToBoard(RowList sinkingPiece) {
    playField.addAll(sinkingPiece);

    double deletedRowIdx = RowDeleter.apply(sinkingPiece, currentPiece, playField, recordKeeping, tetrisGUI);

    sinkingPieces.remove(sinkingPiece);

    if (deletedRowIdx < TetrisConstants.topRow())
      new SinkingPieceFinder().find(deletedRowIdx, playField, sinkingPieces);
  }

  private void addPieceToBoard(TetrisPiece piece) {
    if (movementTimer != null) movementTimer.stop();
    if (rotationTimer != null) rotationTimer.stop();

    playField.addAll(piece.getBlocks());

    double deletedRowIdx = RowDeleter.apply(piece.getBlocks(), piece, playField, recordKeeping, tetrisGUI);

    if (deletedRowIdx < TetrisConstants.topRow())
      new SinkingPieceFinder().find(deletedRowIdx, playField, sinkingPieces);

    tetrominoQueue.resetCurrentPiece(piece);

    if (!PlacementTester.cellsCanBeOccupied(piece, playField))
      tetrisGUI.endGame();
  }

  void dropCurrentPiece() {
    translatePiece(0, TetrisConstants.down());
  }

  private void translatePiece(int x, int y) {
    if (Translater.translate(currentPiece, playField, x, y, false)) {
      tetrisGUI.getBoardCompositor().repaint();

      currentPiece.gettSpinTracker().reset();
    } else if (y == TetrisConstants.down()) {
      handleMovementTimer();
    }
  }

  private void handleMovementTimer() {
    boolean canDrop = Translater.translate(currentPiece, playField, 0, TetrisConstants.down(), true);

    if (canDrop) {
      if (null != movementTimer && movementTimer.isRunning()) movementTimer.stop();

      return;
    }

    if (null == movementTimer || !movementTimer.isRunning()) {
      movementTimer = new Timer(TetrisConstants.timerDelay(), e -> addPieceToBoard(currentPiece));
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
    boolean canDrop = Translater.translate(currentPiece, playField, 0, TetrisConstants.down(), true);

    if (null != rotationTimer) {
      rotationTimer.restart();
      rotationTimer.stop();
    }

    if (canDrop) return;

    rotationTimer = new Timer(TetrisConstants.timerDelay(), e -> addPieceToBoard(currentPiece));
    rotationTimer.setRepeats(false);
    rotationTimer.start();
  }

  void keyboardInput(KeyEvent e, boolean shift) {
    if (shift) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          rotate(TetrisConstants.counterClockwise());

          break;
        case KeyEvent.VK_RIGHT:
          rotate(TetrisConstants.clockwise());

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
          translatePiece(TetrisConstants.left(), 0);

          break;
        case KeyEvent.VK_RIGHT:
          translatePiece(TetrisConstants.right(), 0);

          break;
        case KeyEvent.VK_DOWN:
          translatePiece(0, TetrisConstants.down());
          recordKeeping.softDrop();

          break;
        // case KeyEvent.VK_UP:
        //   translatePiece(0, TetrisConstants.up());
        //
        //   break;
        default:
          InputTests.accept(Character.toString(e.getKeyChar()), currentPiece, playField);

          break;
      }
    }
    tetrisGUI.getBoardCompositor().repaint();
  }

  TetrominoQueue getTetrominoQueue() {
    return tetrominoQueue;
  }
}
