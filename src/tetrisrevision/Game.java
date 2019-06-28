package tetrisrevision;

import tetrisrevision.console.DrawBoard;
import tetrisrevision.tetrominos.TetrominoEnum;
import tetrisrevision.tetrominos.Tetromino;

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

//        System.out.println("Game play q[0] is null " + q[0]);

        while (RunTetris.continueGame()) {
            RunTetris.keyboardInput();
        }
    }

    public void initializeVariables() {
        Tetromino[] tetrominos = this.q;
        falling.setFromTetromino(TetrominoEnum.getTetromino());

//        System.out.println("Game.java falling length: " + falling.getPieceLocation().length);

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
        FindSinkingPieces.SinkingPieceBuilder.setPlayField(p);
        MakeNextPieceFallingPiece.setStaticVariables(falling, q);
        ModifyPlayField.setStaticVariables(p, falling, sinkingPieces);
        PlayField.setStaticVariables(sinkingPieces, falling, q, width, height);
        RunTetris.setStaticVariables(falling, q, p, sinkingPieces, new DrawBoard());
        Test.setStaticVariables(p, sinkingPieces, falling, q);
    }

}
