package com.newtetris.pieces;

public class ZPiece extends TetrisPiece {
    private final static int[][][] pieceCoordsByRotation =
            {
                    {
                            {-1,0},  /*0,0*/
                            {0,  1}, {1,  1}
                    },
                    {
                            {0, -1},
                            {-1, 0}, /*0,0*/
                            {-1, 1}
                    }
            };
    private final static int rotationPermutations = 2;

    ZPiece() {
        super(pieceCoordsByRotation, rotationPermutations);
    }

}
