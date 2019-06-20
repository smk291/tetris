package com.newtetris.tetrispiece.shift;

import com.newtetris.playfield.Coords;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.shift.Shift;

public class ShiftUp extends Shift {
    public void apply(TetrisPiece t) {
        applyShift(t, new Coords(0, -1));
    }
}
