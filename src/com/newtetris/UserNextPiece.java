package com.newtetris;

import com.newtetris.pieces.TetrisPiece;
import com.newtetris.pieces.TetrisPiecesEnum;

public class UserNextPiece {
    TetrisPiece nextPiece;

    UserNextPiece() {
        nextPiece = TetrisPiecesEnum.getPiece();
    }

    public TetrisPiece get() {
        return nextPiece;
    }

    public void set(TetrisPiece t) {
        nextPiece = t;
    }
}
