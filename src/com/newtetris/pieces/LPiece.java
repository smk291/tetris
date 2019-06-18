package com.newtetris.pieces;

public class LPiece extends TetrisPiece {
    private final static int[][][] pieceCoordsByRotation =
            {
                    {
                            {0, -1},
                            /*0,0*/
                            {0,  1}, {1,  1}
                    },
                    {
                            {-1, 0},/*0,0*/{1,  0},
                            {-1, 1}
                    },
                    {
                            {-1,-1}, {0, -1},
                                     /*0,0*/
                                     {0,  1}
                    },
                    {
                                              {1, -1},
                            {-1, 0},/*{0,0},*/{1,  0}
                    }
            };
    private final static int rotationPermutations = 4;

    LPiece() {
        super(pieceCoordsByRotation, rotationPermutations);
    }

}
