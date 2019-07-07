package com.tetrisrevision;

import com.tetrisrevision.console.DrawBoard;
import com.tetrisrevision.tetrominos.TetrominoEnum;
import com.tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.ArrayList;

class Game {
    private static ArrayList<ArrayList<Point>> sinkingPieces = new ArrayList<>();
    private Tetromino[] q = new Tetromino[7];
    private TetrisPiece falling = new TetrisPiece(TetrominoEnum.getTetromino());

    void play() {
        initializeVariables();

        while (RunTetris.continueGame()) {
            RunTetris.keyboardInput();
        }
    }

    private void initializeVariables() {
        Tetromino[] tetrominos = this.q;
        falling.setFromTetromino(TetrominoEnum.getTetromino());

        for (int i = 0, tetrominosLength = tetrominos.length; i < tetrominosLength; i++) {
            Tetromino t = tetrominos[i];

            q[i] = TetrominoEnum.getTetromino();
        }

        int width = 10;
        int height = 24;
        PlayField.setStaticVariables(width, height);

        ChangePiece.setStaticVariables(falling, sinkingPieces);
        ChangePiecesAndQueue.setStaticVariables(falling, q);

        FindSinkingPieces.setStaticVariables(sinkingPieces);
        ModifyPlayField.setStaticVariables(falling, sinkingPieces);
        PlayField.setStaticVariables(width, height);
        RunTetris.setStaticVariables(falling, sinkingPieces, new DrawBoard());
        Test.setStaticVariables(falling);
        InputTests.setStaticVariables(falling);
    }
}
