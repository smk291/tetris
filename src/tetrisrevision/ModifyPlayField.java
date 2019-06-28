package tetrisrevision;

import java.awt.*;

public class ModifyPlayField {
    private static PlayField p;
    private static TetrisPiece falling;
    private PieceLocationValidator tester;

    public static void setStaticVariables(PlayField p) {
        ModifyPlayField.p = p;
    }

    public void putPieceOnBoard(Point[] piece) {
        for (Point t : piece) {
            if (tester.pointIsValid(t)) {
                p.fillCell(t);
            }
        }
    }

    public void removePieceFromBoard(Point[] piece) {
        for (Point t : piece) {
            if (tester.pointIsValid(t)) {
                p.fillCell(t);
            }
        }
    }
}
