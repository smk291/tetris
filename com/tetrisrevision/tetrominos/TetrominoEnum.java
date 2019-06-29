package com.tetrisrevision.tetrominos;

import java.util.Random;

public enum TetrominoEnum {
    I(new IPiece()),
    O(new OPiece()),
    L(new LPiece()),
    J(new JPiece()),
    T(new TPiece()),
    S(new SPiece()),
    Z(new ZPiece());

    public static final TetrominoEnum[] piecesArray = values();
    public static final int enumSize = piecesArray.length;
    public static final Random r = new Random();

    Tetromino piece;

    TetrominoEnum(Tetromino piece) {
        this.piece = piece;
    }

    public static Tetromino getTetromino() {
        return piecesArray[r.nextInt(enumSize)].piece;
    }

    public Tetromino get() {
        return this.piece;
    }
}
