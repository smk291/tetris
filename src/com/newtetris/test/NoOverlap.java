package com.newtetris.test;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
import com.newtetris.playfield.PlayField;
import com.newtetris.tetrispiece.TetrisPiece;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class NoOverlap implements BiPredicate<TetrisPiece, PlayField> {
    @Override
    public boolean test(TetrisPiece t, PlayField playField) {
        for (Coords c : t.playFieldCoords()) {
            if (Cell.isValidCell(c) && playField.getCell(c).isFull()) {
                return false;
            }
        }
        return true;
    }
}


