package com.newtetris;

import java.util.Arrays;

public class UserPieceLocationOnBoard extends User {
    public Coords[] get() {
        return pieceCoordsOnBoard;
    }

    public void set(Coords[] bc) {
        pieceCoordsOnBoard = bc;
    }

    public Coords[] LocatePieceOnBoard(Coords[] coords) {
        return Arrays.stream(coords).map(coord -> coord.sum(super.currentPosition)).toArray(Coords[]::new);
    }
}
