package com.tetrisrevision;

import com.tetrisrevision.console.DrawBoard;
import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.ArrayList;

class Game {
    private static ArrayList<ArrayList<Point>> sinkingPieces = new ArrayList<>();
    private ArrayList<TetrominoEnum> q = new ArrayList<>();
    private ArrayList<TetrominoEnum> backupQ = new ArrayList<>();
    private TetrisPiece falling = new TetrisPiece(TetrominoEnum.getTetromino());

    void play() {
        initializeVariables();

        while (RunTetris.continueGame()) {
            RunTetris.keyboardInput();
        }
    }

    private void initializeVariables() {
        falling.setFromTetromino(TetrominoEnum.getTetromino());

        int width = 10;
        int height = 24;

        PlayField.setStaticVariables(width, height);

        ChangePiece.setStaticVariables(falling, sinkingPieces);
        ChangePiecesAndQueue.setStaticVariables(falling, q, backupQ);

        FindSinkingPieces.setStaticVariables(sinkingPieces);
        ModifyPlayField.setStaticVariables(falling, sinkingPieces);
        PlayField.setStaticVariables(width, height);
        RunTetris.setStaticVariables(falling, sinkingPieces, new DrawBoard());
        Test.setStaticVariables(falling);
        InputTests.setStaticVariables(falling);
    }
}
