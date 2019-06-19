package com.newtetris.board;

import com.newtetris.Coords;

public class ShiftDown {
    public static Coords apply(Coords bc) {
        return bc.sum(0,1);
    }
}
