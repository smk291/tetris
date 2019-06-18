package com.newtetris;

import java.util.Arrays;

public class UserPieceLocationOnBoard {
    Coords[] pieceCoordsOnBoard;

    UserPieceLocationOnBoard(UserCurrentPiece currentPiece, UserCurrentRotation rotation, UserCursor cursor) {
        pieceCoordsOnBoard = LocatePieceOnBoard(currentPiece.getByRotation(rotation.get()), cursor);
    }

    public Coords[] get() {
        return pieceCoordsOnBoard;
    }

    public void set(Coords[] bc) {
        pieceCoordsOnBoard = bc;
    }

    public Coords[] LocatePieceOnBoard(Coords[] coords, UserCursor cursor) {
        return Arrays.stream(coords).map(coord -> coord.sum(cursor.currentPosition)).toArray(Coords[]::new);
    }
}
