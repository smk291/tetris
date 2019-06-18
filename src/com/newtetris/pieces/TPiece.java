package com.newtetris.pieces;

class TPiece extends TetrisPiece {
    private final static int[][][] pieceCoordsByRotation =
            {
                    {
                            {0, -1},
                            {-1, 0},/*0,0*/{1,  0}
                    },
                    {
                            {0, -1},
                            /*0,0*/{1, -1},
                            {0,  1}
                    },
                    {
                            {-1, 0},/*0,0*/{1,  0},
                            {0,  0}
                    },
                    {
                            {0, -1},
                            {-1, 0},/*0,0*/
                            {0,  1}
                    }
            };
    private final static int rotationPermutations = 4;

    TPiece() {
        super(pieceCoordsByRotation, rotationPermutations);
    }

}

