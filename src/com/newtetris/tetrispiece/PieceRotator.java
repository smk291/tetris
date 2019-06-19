package com.newtetris.tetrispiece;

import com.newtetris.pieces.RotationDirection;

abstract public class PieceRotator {
    public static void apply(RotationDirection r, TetrisPiece t) {
        if (r.equals(RotationDirection.LEFT)) {
            RotationIncrementer.left(t);
        } else {
            RotationIncrementer.right(t);
        }
    }

    public void applyLeft(TetrisPiece t) {
        apply(RotationDirection.LEFT, t);
    }

    public void applyRight(TetrisPiece t) {
        apply(RotationDirection.RIGHT, t);
    }
}
