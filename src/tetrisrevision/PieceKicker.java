package tetrisrevision;

public class PieceKicker {
    TetrisPiece falling;
    PieceLocationValidator tester;
    PieceKicker(TetrisPiece falling, PieceLocationValidator tester) {
        this.falling = falling;
        this.tester = tester;
    }

    public boolean tryKick() {
        Integer[][] kickOffsets = falling.getKickData().get(falling.getPrevOrientation()).get(falling.getOrientation());

        for (Integer[] offset : kickOffsets) {
            falling.translateCenter(offset[0], offset[1]);

            if (tester.positionIsValid()) {
                return true;
            }

            falling.translateCenter(-offset[0], -offset[1]);
        }

        return false;
    }
}
