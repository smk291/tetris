package com.newtetris;

import com.newtetris.board.PlayField;
import com.newtetris.pieces.RotationDirection;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.PieceRotator;

abstract public class BoardRotationTester {
    private static boolean rotateTester(
            PlayField playField,
            RotationDirection r,
            TetrisPiece piece
    ) {
        PieceRotator tpr = new PieceRotator();

        if (r.equals(RotationDirection.LEFT)) {
            tpr.applyLeft(piece);
        } else {
            tpr.applyRight(piece);
        }

        return new NoOverlap().test(piece, playField);
    }

    public static boolean left(PlayField playField, TetrisPiece t) {
        return rotateTester(playField, RotationDirection.LEFT, t);
    }

    public static boolean right(PlayField playField, TetrisPiece t) {
        return rotateTester(playField, RotationDirection.RIGHT, t);
    }
}
