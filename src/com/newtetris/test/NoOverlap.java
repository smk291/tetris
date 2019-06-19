package com.newtetris.test;

import com.newtetris.playfield.PlayField;
import com.newtetris.tetrispiece.TetrisPiece;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class NoOverlap implements BiPredicate<TetrisPiece, PlayField> {
    @Override
    public boolean test(TetrisPiece t, PlayField playField) {
        return Arrays.stream(t.pieceCoords()).noneMatch(c -> playField.getCell(c).isFull());
    }
}
