package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;
import com.tetrisrevision.tetrominos.TetrominoEnum;

public class FallingPieceAndQueue {
    private static TetrisPiece falling;
    private static Tetromino[] q;

    public static void setStaticVariables(TetrisPiece falling, Tetromino[] q) {
        FallingPieceAndQueue.falling = falling;
        FallingPieceAndQueue.q = q;
    }

    public static void swap (int i) {
        falling.reset(q[i]);

        q[i] = TetrominoEnum.getTetromino();
    }
}
