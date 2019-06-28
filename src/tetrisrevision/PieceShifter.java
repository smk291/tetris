package tetrisrevision;

import java.awt.*;
import java.util.Arrays;

public class PieceShifter {
    TetrisPiece falling;
    PieceLocationValidator t;

    PieceShifter (TetrisPiece t) {
        this.falling = t;
    }

    public boolean softDropSinkingPiece (Point[] s) {
        Arrays.stream(s).forEach(p -> p.translate(0, 1));

        if (Arrays.stream(s).allMatch(p -> t.pointIsValid(p)))
            return true;

        Arrays.stream(s).forEach(p -> p.translate(0, -1));

        return false;
    }

    public boolean translateFallingPiece (int x, int y) {
        falling.translateCenter(x, y);

        if (t.positionIsValid()) {
            return true;
        }

        falling.translateCenter(-x, -y);

        return false;
    }
}
