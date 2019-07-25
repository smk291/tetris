package com.tetrisrevision;

import javax.swing.*;
import java.util.ArrayList;

class RunTetris {
  private TetrisPiece currentPiece;
  private TetrominoQueue tetrominoQueue;
  private Blocks2d blocks2d;
  private SinkingPieces sinkingPieces;
  private TetrisGUI tetrisGUI;
  private Timer movementTimer;
  private Timer rotationTimer;
  private GameRecordKeeping recordKeeping;

  RunTetris(int width, int height) {
    tetrominoQueue = new TetrominoQueue();
    currentPiece = new TetrisPiece();
    blocks2d = new Blocks2d(width, height);
    sinkingPieces = new SinkingPieces();
    tetrominoQueue.resetCurrentPiece(currentPiece);
    recordKeeping = new GameRecordKeeping();
  }

  GameRecordKeeping getRecordKeeping() {
    return recordKeeping;
  }

  TetrisPiece getCurrentPiece() {
    return currentPiece;
  }

  Blocks2d getBlocks2d() {
    return blocks2d;
  }

  SinkingPieces getSinkingPieces() {
    return sinkingPieces;
  }

  void setTetrisGUI(TetrisGUI t) {
    this.tetrisGUI = t;
  }

  void dropSinkingPieces() {
    for (int i = 0;
        sinkingPieces.getPieces().size() > 0 && i < sinkingPieces.getPieces().size();
        i++) {
      ArrayList<Cell> sinkingPiece = sinkingPieces.getPieces().get(i);

      boolean canSink = Translater.translate(sinkingPiece, blocks2d, 1);

      if (!canSink) {
        addSinkingPieceToBoard(sinkingPiece);

        i--;

        tetrisGUI.getBoardCompositor().repaint();
      }

      tetrisGUI.getBoardCompositor().repaint();
    }
  }

  private void addSinkingPieceToBoard(ArrayList<Cell> sinkingPiece) {
    blocks2d.insert(sinkingPiece);

    int deletedRowIdx = RowDeleter.apply(sinkingPiece, currentPiece, blocks2d, recordKeeping, tetrisGUI);

    sinkingPieces.getPieces().remove(sinkingPiece);

    if (deletedRowIdx > 0) new SinkingPieceFinder().find(deletedRowIdx, blocks2d, sinkingPieces);
  }

  private void addPieceToBoard(TetrisPiece piece) {
    blocks2d.insert(piece);

    int deletedRowIdx = RowDeleter.apply(piece, blocks2d, recordKeeping, tetrisGUI);

    if (deletedRowIdx > 0) new SinkingPieceFinder().find(deletedRowIdx, blocks2d, sinkingPieces);

    tetrominoQueue.resetCurrentPiece(piece);

    if (CellTester.cellsCanBeOccupied(piece, blocks2d)) tetrisGUI.endGame();
  }

  void dropCurrentPiece() {
    translatePiece(0, 1);
  }

  void keyboardInput(char command) {
    keyCommands(Character.toString(command));

    tetrisGUI.getBoardCompositor().repaint();
  }

  private void translatePiece(int x, int y) {
    boolean dropping = y == 1;

    if (Translater.translate(currentPiece, blocks2d, x, y, false)) {
      tetrisGUI.getBoardCompositor().repaint();

      if (dropping) currentPiece.gettSpinTracker().reset();
    } else if (dropping) {
      addPieceToBoard(currentPiece);
    }

//    handleMovementTimer();
  }

//  private void handleMovementTimer() {
//    boolean canDrop = Translater.translate(currentPiece, blocks2d, 0, 1, true);
//
//    if (canDrop) {
//      if (null != movementTimer && movementTimer.isRunning())
//        movementTimer.stop();
//
//      return;
//    }
//
//    if (null == movementTimer || !movementTimer.isRunning()) {
//      movementTimer = new Timer(500, e -> addPieceToBoard(currentPiece));
//      movementTimer.setRepeats(false);
//      movementTimer.start();
//    }
//  }

  private void rotate(int incr) {
    boolean canRotate = Rotator.apply(incr, currentPiece, blocks2d);

    if (canRotate) tetrisGUI.getBoardCompositor().repaint();
    else return;

//    handleRotationTimer();
  }

//  private void handleRotationTimer() {
//    boolean canDrop = Translater.translate(currentPiece, blocks2d, 0, 1, true);
//
//    if (canDrop) {
//      if (null != rotationTimer && rotationTimer.isRunning())
//        rotationTimer.stop();
//
//      return;
//    }
//
//    rotationTimer = new Timer(500, e -> addPieceToBoard(currentPiece));
//    rotationTimer.setRepeats(false);
//    rotationTimer.start();
//  }

  private void keyCommands(String command) {
    switch (command) {
      case "h":
        translatePiece(-1, 0);
        break;
      case "l":
        translatePiece(1, 0);
        break;
      case "j":
        translatePiece(0, 1);
        break;
      case "k":
        translatePiece(0, -1);
        break;
      case "[":
        rotate(-1);
        break;
      case "]":
        rotate(1);
        break;
      case "J":
        Translater.hardDrop(currentPiece, blocks2d);
        addPieceToBoard(currentPiece);

        break;
      default:
        InputTests.accept(command, currentPiece, blocks2d);

        break;
    }
  }
}

