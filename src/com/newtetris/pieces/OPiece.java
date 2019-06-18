package com.newtetris.pieces;

public class OPiece extends TetrisPiece {
    private final static int[][][] pieceCoordsByRotation =
            {
                    {
                            {0, -1}, {1,  1},
                            /*0,0*/{0,  1}
                    }
            };
    private final static int rotationPermutations = 1;

    OPiece() {
        super(pieceCoordsByRotation, rotationPermutations);

    }
}
