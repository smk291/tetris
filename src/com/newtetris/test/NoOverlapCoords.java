package com.newtetris.test;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
import com.newtetris.playfield.PlayField;

import java.util.function.BiPredicate;

public class NoOverlapCoords implements BiPredicate<Coords, PlayField> {
    @Override
    public boolean test(Coords c, PlayField playField) {
        if (Cell.isValidCell(c) && playField.getCell(c).isFull()) {
            return false;
        }

        return true;
    }
}
