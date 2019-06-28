package tetrisrevision;

import java.awt.*;
import java.util.Arrays;

public class PieceShifter {
    private static TetrisPiece falling;
    private static PieceLocationValidator locationValidator;

    public static void setStaticVariables (TetrisPiece falling, PieceLocationValidator locationValidator) {
        PieceShifter.falling = falling;
        PieceShifter.locationValidator = locationValidator;
    }

    public boolean softDropSinkingPiece (Point[] s) {
        Arrays.stream(s).forEach(p -> p.translate(0, 1));

        if (Arrays.stream(s).allMatch(p -> locationValidator.pointIsValid(p)))
            return true;

        Arrays.stream(s).forEach(p -> p.translate(0, -1));

        return false;
    }

    public boolean translateFallingPiece (int x, int y) {
        falling.translateCenter(x, y);

        if (locationValidator.positionIsValid()) {
            return true;
        }

        falling.translateCenter(-x, -y);

        return false;
    }
}
