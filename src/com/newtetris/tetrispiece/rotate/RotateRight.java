package com.newtetris.tetrispiece.rotate;

import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.rotate.Rotate;

public class RotateRight extends Rotate {
    public void apply(TetrisPiece t) {
        applyRotate(t, -1);
    }
}
