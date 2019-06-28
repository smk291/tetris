package tetrisrevision;

public class PieceRotator {
    private static TetrisPiece falling;
    private static PieceLocationValidator tester;
    private static PieceShifter s;
    private static PieceKicker kicker;

    public static void setStaticVariables(TetrisPiece falling, PieceLocationValidator tester, PieceKicker kicker) {
        PieceRotator.falling = falling;
        PieceRotator.tester = tester;
        PieceRotator.kicker = kicker;
    }

    public boolean rotate (int incr) {
        int oldOrientation = falling.getOrientation();
        falling.incrOrientation(incr);

        if (falling.getOrientation() < 0) {
            falling.setOrientation(falling.getOrientationMax() - 1);
        } else if (falling.getOrientation() >= falling.getOrientationMax()) {
            falling.setOrientation(0);
        }

        if (!tester.positionIsValid()) {
            if (kicker.tryKick())
                return true;

            falling.incrOrientation(oldOrientation);

            return false;
        }

        falling.setPrevOrientation(oldOrientation);

        return true;
    }
}
