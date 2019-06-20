package com.newtetris.test;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.PlayField;
import com.newtetris.tetrispiece.TetrisPiece;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class NoOverlap implements BiPredicate<TetrisPiece, PlayField> {
    @Override
    public boolean test(TetrisPiece t, PlayField playField) {
//        System.out.println("No overlap?: ");

//        for (Coords c : t.playfieldCoords()) {
//            System.out.print("(" + c.getX() + ", " + c.getY() + ")");
//        }

//        System.out.println();

        return Arrays
                .stream(t.playfieldCoords())
                .noneMatch(c ->
                        Cell.isValidCell(c) &&
                                playField.getCell(c).isFull()
                );
    }
}
