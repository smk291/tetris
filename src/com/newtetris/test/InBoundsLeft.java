package com.newtetris.test;

import com.newtetris.tetrispiece.TetrisPiece;

import java.util.Arrays;
import java.util.function.Predicate;

public class InBoundsLeft implements Predicate<TetrisPiece> {
    public boolean test(TetrisPiece t) {
        return Arrays.stream(t.pieceCoords()).allMatch(BoundsTesters::left);
    }
}
