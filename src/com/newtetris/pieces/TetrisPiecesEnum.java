package com.newtetris.pieces;

import java.util.Random;

public enum TetrisPiecesEnum {
    I(new IPiece()),
    O(new OPiece()),
    L(new LPiece()),
    J(new JPiece()),
    T(new TPiece()),
    S(new SPiece()),
    Z(new ZPiece());

    public static final TetrisPiecesEnum[] piecesArray = values();
    public static final int enumSize = piecesArray.length;
    public static final Random r = new Random();

    Tetromino piece;

    TetrisPiecesEnum(Tetromino piece) {
        this.piece = piece;
    }

    public static Tetromino getPiece() {
        return piecesArray[r.nextInt(enumSize)].piece;
    }

    public Tetromino get() {
        return this.piece;
    }
}
