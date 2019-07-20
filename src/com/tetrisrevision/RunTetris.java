package com.tetrisrevision;

import com.tetrisrevision.console.DrawToConsole;
import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

class RunTetris {
  // private static int[] delayPerLevel = {500, 450, 400, 350, 300, 250, 200, 150, 100, 50};
  // private Timer t;
  // private int currentLevel;
  // private Timer timer;
  private TetrisPiece falling;
  private ArrayList<ArrayList<Point>> sinkingPieces;
  private String lastCommand;
  private TetrominoQueue tetrominoQueue;
  private PlayField playField;

  RunTetris(int width, int height) {
    playField = new PlayField(new ArrayList<>(), width, height);
    sinkingPieces = new ArrayList<>();
    falling = new TetrisPiece(TetrominoEnum.getTetromino());
    falling.setFromTetromino(TetrominoEnum.getTetromino());
    tetrominoQueue = new TetrominoQueue(falling);
  }

  boolean continueGame() {
    new DrawToConsole().drawBoardIncludingPiece(falling, sinkingPieces, playField);
    // In order to drop the board to the console, I add the falling and sinking piece to the board,
    // draw it, and then remove them again.
    // It's dumb but also the most efficient way
    AddAndRemove.removeFallingPiece(falling, playField);
    AddAndRemove.removeSinkingPieces(sinkingPieces, playField);
    softDropSinkingPieces();
    PieceMover.trySoftDropSinkingPieces(sinkingPieces, playField);

    return softDropFallingPiece(falling, playField);
  }

  // Drop each sinking piece one space -- replace with hard drop
  private void softDropSinkingPieces() {
    for (int i = 0; sinkingPieces.size() > 0 && i < sinkingPieces.size(); i++) {
      ArrayList<Point> sinkingPiece = sinkingPieces.get(i);

      boolean canSink = PieceMover.trySoftDropSinkingPiece(sinkingPiece, playField);

      if (!canSink) {
        AddAndRemove.addSinkingPiece(sinkingPiece, playField);
        int searchFrom = RowDeleter.apply(sinkingPiece, playField);

        sinkingPieces.remove(sinkingPiece);

        if (searchFrom > 0)
          new SinkingPieceDetector().resetVariablesAndRunSearch(searchFrom, playField, sinkingPieces);

        i--;

        new DrawToConsole().drawBoardIncludingPiece(falling, sinkingPieces, playField);
      }

      PieceMover.tryRaiseSinkingPiece(sinkingPiece, playField);
    }
  }

  // Try lowering the falling piece
  // If it can't be lowered and addToBoard is true, add it to board
  // Otherwise it can still move
  // After it's inserted into the board, piece resets
  // If position isn't valid after piece resets, game is over
  private boolean softDropFallingPiece(TetrisPiece piece, PlayField field) {
    boolean canDrop = PieceMover.tryTranslateFallingPiece(piece, field,0, 1);

    if (!canDrop
        &&
        // falling piece can't drop further
        falling.isAddToBoard()
        &&
        // press 'down' to add the piece to the board immediately if it can't drop further
        (lastCommand.equals("j") || (lastCommand.equals("J")))) {
      AddAndRemove.addFallingPiece(falling, playField);

      int searchFrom = RowDeleter.apply(falling.getPieceLocation(), playField);

      if (searchFrom > 0) new SinkingPieceDetector().resetVariablesAndRunSearch(searchFrom, field, sinkingPieces);

      tetrominoQueue.getNextPiece();

      if (!Position.isInBoundsAndEmptyNoRowMin(piece, field)) return false;
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
        PieceMover.tryTranslateFallingPiece(falling, playField, -1, 0);
        break;
      case "l":
        PieceMover.tryTranslateFallingPiece(falling, playField, 1, 0);
        break;
      case "j":
        PieceMover.tryTranslateFallingPiece(falling, playField, 0, 1);
        break;
      case "k":
        PieceMover.tryTranslateFallingPiece(falling, playField, 0, -1);
        break;
      case "[":
        Rotator.tryRotate(-1, falling, playField);
        break;
      case "]":
        Rotator.tryRotate(1, falling, playField);
        break;
      case "J":
        PieceMover.hardDrop(falling, playField);

        break;
      default:
        InputTests.accept(command, falling, playField);

        break;
    }
  }
}
