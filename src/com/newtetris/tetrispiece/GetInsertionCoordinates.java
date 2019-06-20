package com.newtetris.tetrispiece;

import com.newtetris.playfield.Coords;

import java.util.stream.IntStream;

abstract public class GetInsertionCoordinates {
    public static Coords[] apply(TetrisPiece t) {
        int pieceSize = t.getTetromino().getPieceSize();
        Coords center = t.getCenter();
        Coords[] shape = t.getShape();

        return IntStream.range(0, pieceSize)
                .mapToObj(i -> shape[i].sum(center))
                .toArray(Coords[]::new);
    }
}
