package tetrisrevision;

import tetrisrevision.tetrominos.Tetromino;

public class MakeNextPieceFallingPiece {
    private static TetrisPiece falling;
    private static Tetromino[] q;

    MakeNextPieceFallingPiece(){

    }

    public static void setStaticVariables (TetrisPiece falling, Tetromino[] q) {
       MakeNextPieceFallingPiece.falling = falling;
       MakeNextPieceFallingPiece.q = q;
    }

}
