package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

class RunTetris {
  // private static int[] delayPerLevel = {500, 450, 400, 350, 300, 250, 200, 150, 100, 50};
  // private Timer t;
  // private int currentLevel;
  // private Timer timer;
  private GUI gui;
  private TetrisPiece falling;
  private ArrayList<ArrayList<Point>> sinkingPieces;
  private String lastCommand;
  private ModifyPlayField modifyPlayField;
  private FindSinkingPieces findSinkingPieces;
  private InputTests inputTests;
  private Test test;
  private ChangePiece changePiece;
  private ChangePiecesAndQueue changePiecesAndQueue;

  RunTetris(
      TetrisPiece falling,
      ArrayList<ArrayList<Point>> sinkingPieces,
      GUI gui,
      ModifyPlayField modifyPlayField,
      PlayField playField,
      Test test,
      FindSinkingPieces findSinkingPieces,
      ChangePiecesAndQueue changePiecesAndQueue,
      ChangePiece changePiece) {
    this.falling = falling;
    this.sinkingPieces = sinkingPieces;
    this.gui = gui;
    this.modifyPlayField = modifyPlayField;
    this.findSinkingPieces = findSinkingPieces;
    this.inputTests = new InputTests(playField, falling);
    this.test = test;
    this.changePiece = changePiece;
    this.changePiecesAndQueue = changePiecesAndQueue;
  }

  boolean continueGame() {
    gui.drawBoardIncludingPiece();
    // In order to drop the board to the console, I add the falling and sinking piece to the board,
    // draw it, and then remove them again.
    // It's dumb but also the most efficient way
    modifyPlayField.addAndRemove.removeFallingPiece();
    modifyPlayField.addAndRemove.removeSinkingPieces();
    softDropSinkingPieces();
    changePiece.position.trySoftDropSinkingPieces();

    return softDropFallingPiece();
  }

  // Drop each sinking piece one space -- replace with hard drop
  private void softDropSinkingPieces() {
    for (int i = 0; sinkingPieces.size() > 0 && i < sinkingPieces.size(); i++) {
      ArrayList<Point> piece = sinkingPieces.get(i);

      boolean canSink = changePiece.position.trySoftDropSinkingPiece(piece);

      if (!canSink) {
        modifyPlayField.addAndRemove.addSinkingPiece(piece);
        int searchFrom = modifyPlayField.rowDeleter.apply(piece);

        sinkingPieces.remove(piece);

        if (searchFrom > 0) findSinkingPieces.resetVariablesAndRunSearch(searchFrom);

        i--;

        gui.drawBoardIncludingPiece();
      }

      changePiece.position.tryRaiseSinkingPiece(piece);
    }
  }

  // Try lowering the falling piece
  // If it can't be lowered and addToBoard is true, add it to board
  // Otherwise it can still move
  // After it's inserted into the board, piece resets
  // If position isn't valid after piece resets, game is over
  private boolean softDropFallingPiece() {
    boolean canDrop = changePiece.position.tryTranslateFallingPiece(0, 1);

    if (!canDrop
        &&
        // falling piece can't drop further
        falling.isAddToBoard()
        &&
        // press 'down' to add the piece to the board immediately if it can't drop further
        (lastCommand.equals("j") || (lastCommand.equals("J")))) {
      modifyPlayField.addAndRemove.addFallingPiece();

      int searchFrom = modifyPlayField.rowDeleter.apply(falling.getPieceLocation());

      if (searchFrom > 0) findSinkingPieces.resetVariablesAndRunSearch(searchFrom);

      changePiecesAndQueue.getNextPiece();

      if (!test.position.isInBoundsAndEmptyNoRowMin()) return false;
    }
    if (!canDrop) {
      falling.setAddToBoard(true);
    } else {
      falling.setAddToBoard(false);
      falling.getCenter().translate(0, -1);
    }

    return true;
  }

  void keyboardInput() {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Command:");
    String command = keyboard.nextLine();

    lastCommand = command;

    switch (command) {
      case "h":
        changePiece.position.tryTranslateFallingPiece(-1, 0);
        break;
      case "l":
        changePiece.position.tryTranslateFallingPiece(1, 0);
        break;
      case "j":
        changePiece.position.tryTranslateFallingPiece(0, 1);
        break;
      case "k":
        changePiece.position.tryTranslateFallingPiece(0, -1);
        break;
      case "[":
        changePiece.rotation.tryRotate(-1);
        break;
      case "]":
        changePiece.rotation.tryRotate(1);
        break;
      case "J":
        changePiece.position.hardDrop();

        break;
      default:
        inputTests.accept(command);

        break;
    }
  }
}
