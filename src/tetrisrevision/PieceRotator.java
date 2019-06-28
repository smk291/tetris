package tetrisrevision;

public class PieceRotator {
    TetrisPiece falling;
    PieceLocationValidator t;
    PieceShifter s;
    PieceKicker kicker;

    PieceRotator(TetrisPiece falling, PieceLocationValidator t, PieceKicker kicker) {
        this.falling = falling;
        this.t = t;
        this.kicker = kicker;
    }

    public boolean rotate (int incr) {
        int oldOrientation = falling.getOrientation();
        falling.incrOrientation(incr);

        if (falling.getOrientation() < 0) {
            falling.setOrientation(falling.getOrientationMax() - 1);
        } else if (falling.getOrientation() >= falling.getOrientationMax()) {
            falling.setOrientation(0);
        }

        if (!t.positionIsValid()) {
            if (kicker.tryKick())
                return true;

            falling.incrOrientation(oldOrientation);

            return false;
        }

        falling.setPrevOrientation(oldOrientation);

        return true;
    }
}
