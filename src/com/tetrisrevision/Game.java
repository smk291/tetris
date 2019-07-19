package com.tetrisrevision;

import com.tetrisrevision.console.DrawBoard;
import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

class Game {
  private static ArrayList<ArrayList<Point>> sinkingPieces = new ArrayList<>();
  private ArrayList<TetrominoEnum> q = new ArrayList<>();
  private ArrayList<TetrominoEnum> backupQ = new ArrayList<>();
  private TetrisPiece falling = new TetrisPiece(TetrominoEnum.getTetromino());
  private ArrayList<Cell> playFieldCells = new ArrayList<>();
  private PlayField playField;
  private Test test;
  private ChangePiece changePiece;
  private FindSinkingPieces findSinkingPieces;
  private ModifyPlayField modifyPlayField;
  private RunTetris runTetris;
  private ChangePiecesAndQueue changePiecesAndQueue;

  Game() {
    falling.setFromTetromino(TetrominoEnum.getTetromino());

    int width = 10;
    int height = 24;
    playField = new PlayField(playFieldCells, width, height);
    test = new Test(falling, playField);
    changePiece = new ChangePiece(falling, sinkingPieces, test);
    changePiecesAndQueue = new ChangePiecesAndQueue(falling, q, backupQ);
    findSinkingPieces = new FindSinkingPieces(playField, sinkingPieces, test);
    modifyPlayField = new ModifyPlayField(falling, sinkingPieces, playField);
    runTetris = new RunTetris(falling, sinkingPieces, new DrawBoard(playField, modifyPlayField), modifyPlayField, playField, test, findSinkingPieces, changePiecesAndQueue, changePiece);
  }

  void play() {
    while (runTetris.continueGame()) {
      runTetris.keyboardInput();
    }
  }
}
