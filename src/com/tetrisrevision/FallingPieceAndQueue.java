package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;
import com.tetrisrevision.tetrominos.TetrominoEnum;

class FallingPieceAndQueue {
    private static TetrisPiece falling;
    private static Tetromino[] q;

    static void setStaticVariables(TetrisPiece falling, Tetromino[] q) {
        FallingPieceAndQueue.falling = falling;
        FallingPieceAndQueue.q = q;
    }

    static void swap(int i) {
        falling.reset(q[i]);

        q[i] = TetrominoEnum.getTetromino();
    }
}
