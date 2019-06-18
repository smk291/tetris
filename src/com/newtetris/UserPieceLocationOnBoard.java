package com.newtetris;

public class UserPieceLocationOnBoard extends User {
    public Coords[] get() {
        return pieceCoordsOnBoard;
    }

    public void set(Coords[] bc) {
        pieceCoordsOnBoard = bc;
    }
}
