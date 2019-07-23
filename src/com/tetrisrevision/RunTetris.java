package com.tetrisrevision;

import java.util.ArrayList;
import java.util.Arrays;

class RunTetris {
  private TetrisPiece currentPiece2d;
  private String prevCommand;
  private TetrominoQueue tetrominoQueue;
  private Blocks2d blocks2d;
  private SinkingPieces sinkingPieces;
  private TetrisGUI tetrisGUI;

  RunTetris(int width, int height) {
    tetrominoQueue = new TetrominoQueue();
    currentPiece2d = new TetrisPiece();
    blocks2d = new Blocks2d(width, height);
    sinkingPieces = new SinkingPieces();
    tetrominoQueue.resetCurrentPiece(currentPiece2d);
  }

  boolean continueGame() {
//    while (sinkingPieces.getPieces().size() > 0) {
//      handleSinkingPieces2d();
//
//      sinkingPieces.softDropPieces(blocks2d);
//
//      tetrisGUI.getBoardCompositor().repaint();
//
//      handleSinkingPieces2d();
//
      tetrisGUI.getBoardCompositor().repaint();
//    }

    return handleCurrentPiece2d(currentPiece2d, blocks2d);
  }

  void handleSinkingPieces2d() {
    for(int i = 0; sinkingPieces.getPieces().size() > 0 && i < sinkingPieces.getPieces().size(); i++) {
      ArrayList<Cell> sinkingPiece = sinkingPieces.getPieces().get(i);

      boolean canSink = Translater.translate(sinkingPiece, blocks2d,  1);

      if (!canSink) {
        blocks2d.addPieceToBlocks(sinkingPiece.toArray(new Cell[0]));

        int searchFrom = RowDeleter.apply(sinkingPiece, blocks2d);

        sinkingPieces.getPieces().remove(sinkingPiece);

        if (searchFrom > 0)
          new SinkingPieceDetector().find(searchFrom, blocks2d, sinkingPieces);

        i--;

        tetrisGUI.getBoardCompositor().repaint();
      }

      tetrisGUI.getBoardCompositor().repaint();
    }
  }

  private boolean handleCurrentPiece2d(TetrisPiece piece, Blocks2d blocks2d) {
    boolean canDrop = Translater.translate(piece, blocks2d, 0, 1);

    if (!canDrop &&
        piece.getAddToBoard() &&
        (prevCommand.equals("j") || (prevCommand.equals("J")))
    ) {
      blocks2d.addPieceToBlocks(piece.getCells());

      int searchFrom = RowDeleter.apply(piece.getCells(), blocks2d);

      if (searchFrom > 0)
        new SinkingPieceDetector().find(searchFrom, blocks2d, sinkingPieces);

      tetrominoQueue.resetCurrentPiece(piece);

      if (!CellTester.emptyAndInBoundsAndNoOverlapNoMin(piece, blocks2d))
        return false;
    }
    if (!canDrop) {
      piece.setAddToBoard(true);
    } else {
      piece.setAddToBoard(false);
      piece.getCenter().translate(0, -1);
    }

    tetrisGUI.getBoardCompositor().repaint();

    return true;
  }

  void keyboardInput(char command) {
    this.prevCommand = Character.toString(command);

    swingCommand(Character.toString(command));
    continueGame();
    tetrisGUI.getBoardCompositor().repaint();
  }

  private void swingCommand(String command) {
    switch (command) {
      case "h":
        Translater.translate(currentPiece2d, blocks2d, -1, 0);
        break;
      case "l":
        Translater.translate(currentPiece2d, blocks2d, 1, 0);
        break;
      case "j":
        Translater.translate(currentPiece2d, blocks2d, 0, 1);
        break;
      case "k":
        Translater.translate(currentPiece2d, blocks2d, 0, -1);
        break;
      case "[":
        Rotator.apply(-1, currentPiece2d, blocks2d);
        break;
      case "]":
        Rotator.apply(1, currentPiece2d, blocks2d);
        break;
      case "J":
        Translater.hardDrop(currentPiece2d, blocks2d);
        break;
      default:
        InputTests.accept(command, currentPiece2d, blocks2d);
        break;
    }
  }

  TetrisPiece getCurrentPiece2d() {
    return currentPiece2d;
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
}
