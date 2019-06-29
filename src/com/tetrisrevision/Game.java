package com.tetrisrevision;

import com.tetrisrevision.console.DrawBoard;
import com.tetrisrevision.tetrominos.TetrominoEnum;
import com.tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    private static int width = 10;
    private static int height = 24;

    private static ArrayList<ArrayList<Point>> sinkingPieces = new ArrayList<>();
    private Tetromino[] q = new Tetromino[7];
    private PlayField p = new PlayField();
    private TetrisPiece falling = new TetrisPiece(TetrominoEnum.getTetromino());

    Game() {
    }

    public void play() {
        initializeVariables();

        while (RunTetris.continueGame()) {
            RunTetris.keyboardInput();
        }
    }

    public void initializeVariables() {
        Tetromino[] tetrominos = this.q;
        falling.setFromTetromino(TetrominoEnum.getTetromino());

        for (int i = 0, tetrominosLength = tetrominos.length; i < tetrominosLength; i++) {
            Tetromino t = tetrominos[i];

            q[i] = TetrominoEnum.getTetromino();
        }

        PlayField.setStaticVariables(sinkingPieces,falling, q, width, height);
        TetrisPiece.setStaticVariables(p);

        Cell.setStaticVariables(p);
        Change.setStaticVariables(falling, sinkingPieces);
        FallingPieceAndQueue.setStaticVariables(falling, q);

        DrawBoard.setStaticVariables(p);
        FindSinkingPieces.setStaticVariables(p, sinkingPieces);
        ModifyPlayField.setStaticVariables(p, falling, sinkingPieces);
        PlayField.setStaticVariables(sinkingPieces, falling, q, width, height);
        RunTetris.setStaticVariables(falling, p, sinkingPieces, new DrawBoard());
        Test.setStaticVariables(p, sinkingPieces, falling, q);
        InputTests.setStaticVariables(falling);
    }
}
