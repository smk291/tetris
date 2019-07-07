package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;
import com.tetrisrevision.tetrominos.TetrominoEnum;

/****
 *
 * This class will contain the logic that swaps out pieces and randomizes
 * the queue according to official tetris guidelines.
 *
 * Currently it just resets the falling piece, using the first tetromino in the seven-tetromino queue
 *
 */

class ChangePiecesAndQueue {
    private static TetrisPiece falling;
    private static Tetromino[] q;

    static void setStaticVariables(TetrisPiece falling, Tetromino[] q) {
        ChangePiecesAndQueue.falling = falling;
        ChangePiecesAndQueue.q = q;
    }

    static void swap(int i) {
        falling.reset(q[i]);

        q[i] = TetrominoEnum.getTetromino();
    }
}
