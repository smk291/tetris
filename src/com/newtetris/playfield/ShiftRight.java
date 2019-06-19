package com.newtetris.playfield;

import com.newtetris.Coords;

public class ShiftRight {
    public static Coords apply(Coords bc) {
        return bc.sum(1,0);
    }
}
