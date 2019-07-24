package com.tetrisrevision;

import javax.swing.*;
import java.util.ArrayList;

class RunTetris {
  private TetrisPiece currentPiece;
  private TetrominoQueue tetrominoQueue;
  private Blocks2d blocks2d;
  private SinkingPieces sinkingPieces;
  private TetrisGUI tetrisGUI;
  private boolean continueGame = true;
  private Timer movementTimer =
      new Timer(
          1000,
          e -> {
            boolean canDrop = Translater.translate(currentPiece, blocks2d, 0, 1, true);

            if (!canDrop) addPieceToBoard(currentPiece);
          });
  private Timer rotationTimer =
      new Timer(
          2500,
          e -> {
            boolean canDrop = Translater.translate(currentPiece, blocks2d, 0, 1, true);

            if (!canDrop) addPieceToBoard(currentPiece);
          });

  RunTetris(int width, int height) {
    tetrominoQueue = new TetrominoQueue();
    currentPiece = new TetrisPiece();
    blocks2d = new Blocks2d(width, height);
    sinkingPieces = new SinkingPieces();
    tetrominoQueue.resetCurrentPiece(currentPiece);

    movementTimer.setDelay(1000);
    movementTimer.setRepeats(false);
    rotationTimer.setDelay(2500);
    rotationTimer.setRepeats(false);
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

    int deletedRowIdx = RowDeleter.apply(sinkingPiece, blocks2d);

    sinkingPieces.getPieces().remove(sinkingPiece);

    if (deletedRowIdx > 0) new SinkingPieceFinder().find(deletedRowIdx, blocks2d, sinkingPieces);
  }

  private void addPieceToBoard(TetrisPiece piece) {
    blocks2d.insert(piece);

    int deletedRowIdx = RowDeleter.apply(piece, blocks2d);

    if (deletedRowIdx > 0) new SinkingPieceFinder().find(deletedRowIdx, blocks2d, sinkingPieces);

    tetrominoQueue.resetCurrentPiece(piece);

    if (CellTester.emptyAndInBoundsAndNoOverlapNoMin(piece, blocks2d)) continueGame = false;
  }

  void dropCurrentPiece() {
    translateCurrentPiece(0, 1);
  }

  void keyboardInput(char command) {
    swingCommand(Character.toString(command));

    tetrisGUI.getBoardCompositor().repaint();
  }

  private void translateCurrentPiece(int x, int y) {
    boolean dropping = y == 1;

    if (Translater.translate(currentPiece, blocks2d, x, y, false)) {
      tetrisGUI.getBoardCompositor().repaint();
    } else if (dropping) {
      addPieceToBoard(currentPiece);
    }

    boolean canDrop = Translater.translate(currentPiece, blocks2d, 0, 1, true);

    if (!canDrop && !movementTimer.isRunning()) movementTimer.start();
    else {
      movementTimer.stop();

      movementTimer.restart();
    }
  }

  private void rotate(int incr) {
    boolean canRotate = Rotator.apply(incr, currentPiece, blocks2d);

    tetrisGUI.getBoardCompositor().repaint();

    if (!canRotate) {
      boolean canLift = Translater.translate(currentPiece, blocks2d, 0, -1, false); // superfluous?

      if (canLift) canRotate = Rotator.apply(incr, currentPiece, blocks2d);
    }

    rotationTimer.restart();

    if (canRotate && rotationTimer.isRunning()) {
      rotationTimer.stop();
    } else if (canRotate && !rotationTimer.isRunning()) {
      rotationTimer.start();
    } else {
      rotationTimer.stop();
    }
  }

  private void swingCommand(String command) {
    switch (command) {
      case "h":
        translateCurrentPiece(-1, 0);
        break;
      case "l":
        translateCurrentPiece(1, 0);
        break;
      case "j":
        translateCurrentPiece(0, 1);
        break;
      case "k":
        translateCurrentPiece(0, -1);
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
