package com.newtetris.tetrispiece.shift;

import com.newtetris.playfield.Coords;

import java.util.ArrayList;

public class ShiftUpCoords {
    public void apply (ArrayList<Coords> cs) {
        for (Coords c : cs) {
            c.mutateSum(0, -1);
        }
    }
}
