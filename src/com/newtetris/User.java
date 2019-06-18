package com.newtetris;

import com.newtetris.pieces.TetrisPiece;

public class User {
    Coords currentPosition;
    int currentRotation;
    TetrisPiece currentPiece;
    Coords[] pieceCoordsOnBoard;
    TetrisPiece nextPiece;

    User(Coords c) {
        currentPosition = c;
    }

    User() {
        currentPosition = new Coords(4, 0);
    }
}

