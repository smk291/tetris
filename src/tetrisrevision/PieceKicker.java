package tetrisrevision;

public class PieceKicker {
    private static TetrisPiece falling;
    private static PieceLocationValidator tester;

    PieceKicker() {}

    public static void setStaticVariables(TetrisPiece falling, PieceLocationValidator tester) {
        PieceKicker.falling = falling;
        PieceKicker.tester = tester;
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
