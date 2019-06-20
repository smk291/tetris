package com.newtetris.tetrispiece.shift;

import com.newtetris.playfield.Coords;
import com.newtetris.tetrispiece.TetrisPiece;

public class ShiftRight extends Shift {
    public void apply(TetrisPiece t) {
        applyShift(t, new Coords(1, 0));
    }
}
