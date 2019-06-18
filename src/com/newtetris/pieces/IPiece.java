package com.newtetris.pieces;

class IPiece extends TetrisPiece {
    private static int[][][] pieceCoordsByRotation =
            {
                    {
                            {-2, 0}, {-1, 0}, /*0,0*/ {1, 0}
                    },
                    {
                            {0, -2},
                            {0, -1},
                            /*0,0*/
                            {0,  1}
                    }
            };
    private final static int rotationPermutations = 2;

    IPiece() {
        super(pieceCoordsByRotation, rotationPermutations);
    }

}
