package com.tetrisrevision;

import com.tetrisrevision.console.DrawBoard;
import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.ArrayList;

class Game {
  private static ArrayList<ArrayList<Point>> sinkingPieces = new ArrayList<>();
  private RunTetris runTetris;

  Game() {
    TetrisPiece falling = new TetrisPiece(TetrominoEnum.getTetromino());
    falling.setFromTetromino(TetrominoEnum.getTetromino());

    int width = 10;
    int height = 24;
    ArrayList<Cell> playFieldCells = new ArrayList<>();
    PlayField playField = new PlayField(playFieldCells, width, height);
    Test test = new Test(falling, playField);
    ChangePiece changePiece = new ChangePiece(falling, sinkingPieces, test);
    ArrayList<TetrominoEnum> q = new ArrayList<>();
    ArrayList<TetrominoEnum> backupQ = new ArrayList<>();
    ChangePiecesAndQueue changePiecesAndQueue = new ChangePiecesAndQueue(falling, q, backupQ);
    FindSinkingPieces findSinkingPieces = new FindSinkingPieces(playField, sinkingPieces, test);
    ModifyPlayField modifyPlayField = new ModifyPlayField(falling, sinkingPieces, playField);
    runTetris =
        new RunTetris(
            falling,
            sinkingPieces,
            new DrawBoard(playField, modifyPlayField),
            modifyPlayField,
            playField,
            test,
            findSinkingPieces,
            changePiecesAndQueue,
            changePiece);
  }

  void play() {
    while (runTetris.continueGame()) {
      runTetris.keyboardInput();
    }
  }
}
