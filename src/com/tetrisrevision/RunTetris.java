package com.tetrisrevision;

import com.tetrisrevision.console.PrintToConsole;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

class RunTetris {
  private TetrisPiece currentPiece;
  private TetrisPiece currentPiece2d;
  private String prevCommand;
  private TetrominoQueue tetrominoQueue;
  private PlayField playField;
  private Blocks2d blocks2d;
  private SinkingPieces sinkingPieces;
  private SinkingPieces2d sinkingPieces2d;
  private static int width = 10;
  private static int height = 24;
  private static int[] delayPerLevel = {500, 450, 400, 350, 300, 250, 200, 150, 100, 50};
  private Timer t;
  private int currentLevel;
  private TetrisGUI tetrisGUI;

  RunTetris(int width, int height) {
    playField = new PlayField(new ArrayList<>(), width, height);
    tetrominoQueue = new TetrominoQueue();
    currentPiece = new TetrisPiece();
    currentPiece2d = new TetrisPiece();
    blocks2d = new Blocks2d(width, height);
    sinkingPieces = new SinkingPieces();
    sinkingPieces2d = new SinkingPieces2d();

    tetrominoQueue.resetCurrentPiece(currentPiece);
    currentPiece2d.reset(currentPiece.getTetromino());
  }

  void play() {
//    t.start();

    while (continueGame()) {
      keyboardInput();
    }
  }

  // In order to cell the field to the console, I add the currentPiece and sinking piece to the
  // field,
  // cell it, and then remove them again. It's dumb but necessary.
  private boolean continueGame() {
    new PrintToConsole().field(currentPiece, sinkingPieces, playField);
    AddAndRemove.remove(currentPiece, playField);
    AddAndRemove.removeFromList(sinkingPieces, playField);

    while (sinkingPieces.getSinkingPieces().size() > 0) {
      handleSinkingPieces();
      handleSinkingPieces2d();

      sinkingPieces.softDropSinkingPieces(playField);
      sinkingPieces2d.softDropPieces(blocks2d);

      tetrisGUI.getBoardCompositor().repaint();
    }

    handleCurrentPiece2d(currentPiece2d, blocks2d);

    return handleCurrentPiece(currentPiece, playField);
  }

  // Drop each sinking piece one space if possible or add to field (replace with hard drop)
  private void handleSinkingPieces() {
    for (int i = 0; sinkingPieces.getSinkingPieces().size() > 0 && i < sinkingPieces.getSinkingPieces().size(); i++) {
      ArrayList<Point> sinkingPiece = sinkingPieces.getSinkingPieces().get(i);

      boolean canSink = Translater.translate(sinkingPiece, playField, 0, 1);

      if (!canSink) {
        AddAndRemove.add(sinkingPiece, playField);
        int searchFrom = RowDeleter.apply(sinkingPiece, playField);

        sinkingPieces.getSinkingPieces().remove(sinkingPiece);

        if (searchFrom > 0)
            new SinkingPieceDetector().find(searchFrom, playField, sinkingPieces);

        i--;

        new PrintToConsole().field(currentPiece, sinkingPieces, playField);
      }

      Translater.translate(sinkingPiece, playField, 0, -1);
    }

  }

  private void handleSinkingPieces2d() {
    for (int i = 0; sinkingPieces2d.getPieces().size() > 0 && i < sinkingPieces2d.getPieces().size(); i++) {
      ArrayList<Cell> sinkingPiece = sinkingPieces2d.getPieces().get(i);

      boolean canSink = Translater2d.translate(sinkingPiece, blocks2d, 0, 1);

      if (!canSink) {
        int searchFrom = RowDeleter2d.apply(sinkingPiece, blocks2d);

        sinkingPieces2d.getPieces().remove(sinkingPiece);

        if (searchFrom > 0)
          new SinkingPieceDetector2d().find(searchFrom, blocks2d, sinkingPieces2d);

        i--;
      }

      Translater2d.translate(sinkingPiece, blocks2d, 0, -1);

      tetrisGUI.getBoardCompositor().repaint();
    }
  }

  // Try lowering currentPiece
  // If it can't be lowered and addToBoard is true, add it to field
  // Otherwise user can still move it
  // After it's inserted into the field, piece resets
  // If position isn't valid after piece resets, game is over
  void dropCurrentPiece() {
    handleCurrentPiece(currentPiece, playField);
    handleCurrentPiece2d(currentPiece2d, blocks2d);

    tetrisGUI.getBoardCompositor().repaint();
  }

  private boolean handleCurrentPiece(TetrisPiece piece, PlayField field) {
    boolean canDrop = Translater.translate(piece, field, 0, 1);

    if (!canDrop &&
        // currentPiece piece can't drop further
        piece.getAddToBoard() &&
        // press 'down' to add the piece to the field immediately if it can't drop further
        (prevCommand.equals("j") || (prevCommand.equals("J")))) {
      AddAndRemove.add(piece, field);

      int searchFrom = RowDeleter.apply(piece.getCells(), field);

      if (searchFrom > 0)
        new SinkingPieceDetector().find(searchFrom, field, sinkingPieces);

      tetrominoQueue.resetCurrentPiece(piece);
      currentPiece2d.reset(piece.getTetromino()); // !----------

      if (!CellTester.emptyAndInBoundsAndNoOverlapNoMin(piece, field)) return false;
    }
    if (!canDrop) {
      piece.setAddToBoard(true);
    } else {
      piece.setAddToBoard(false);
      piece.getCenter().translate(0, -1);
    }

    return true;
  }

  private boolean handleCurrentPiece2d(TetrisPiece piece, Blocks2d blocks2d) {
    boolean canDrop = Translater2d.translate(piece, blocks2d, 0, 1);

    if (!canDrop &&
        piece.getAddToBoard() &&
        (prevCommand.equals("j") || (prevCommand.equals("J")))
    ) {
      blocks2d.addPieceToBlocks(piece);

      int searchFrom = RowDeleter2d.apply(piece.getCells(), blocks2d);

      if (searchFrom > 0)
        new SinkingPieceDetector2d().find(searchFrom, blocks2d, sinkingPieces2d);

      if (!CellTester2d.emptyAndInBoundsAndNoOverlapNoMin(piece, blocks2d))
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

  private void keyboardInput() {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Command:");
    String command = keyboard.nextLine();

    prevCommand = command;

    switch (command) {
      case "h":
        Translater.translate(currentPiece, playField, -1, 0);
        break;
      case "l":
        Translater.translate(currentPiece, playField, 1, 0);
        break;
      case "j":
        Translater.translate(currentPiece, playField, 0, 1);
        break;
      case "k":
        Translater.translate(currentPiece, playField, 0, -1);
        break;
      case "[":
        Rotator.apply(-1, currentPiece, playField);
        break;
      case "]":
        Rotator.apply(1, currentPiece, playField);
        break;
      case "J":
        Translater.hardDrop(currentPiece, playField);
        break;
      default:
        InputTests.accept(command, currentPiece, playField);
        break;
    }

    switch (command) {
      case "h":
        Translater2d.translate(currentPiece2d, blocks2d, -1, 0);
        break;
      case "l":
        Translater2d.translate(currentPiece2d, blocks2d, 1, 0);
        break;
      case "j":
        Translater2d.translate(currentPiece2d, blocks2d, 0, 1);
        break;
      case "k":
        Translater2d.translate(currentPiece2d, blocks2d, 0, -1);
        break;
      case "[":
        Rotator2d.apply(-1, currentPiece2d, blocks2d);
        break;
      case "]":
        Rotator2d.apply(1, currentPiece2d, blocks2d);
        break;
      case "J":
        Translater2d.hardDrop(currentPiece2d, blocks2d);
        break;
      default:
        InputTests2d.accept(command, currentPiece2d, blocks2d);
        break;
    }

    tetrisGUI.getBoardCompositor().repaint();
  }

  TetrisPiece getCurrentPiece2d() {
    return currentPiece2d;
  }

  Blocks2d getBlocks2d() {
    return blocks2d;
  }

  SinkingPieces2d getSinkingPieces2d() {
    return sinkingPieces2d;
  }

  void setTetrisGUI(TetrisGUI t) {
    this.tetrisGUI = t;
  }
}
