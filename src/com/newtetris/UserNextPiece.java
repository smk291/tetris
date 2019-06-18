package com.newtetris;

import com.newtetris.pieces.TetrisPiece;

public class UserNextPiece extends User {
    public TetrisPiece get() {
        return nextPiece;
    }

    public void set(TetrisPiece t) {
        nextPiece = t;
    }
}
