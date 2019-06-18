package com.newtetris.pieces;

public class SPiece extends TetrisPiece {
    private final static int[][][] pieceCoordsByRotation =
            {
                    {
                            {0,  0}, {1,  0},
                            {-1, 1}, {0,  1}
                    },
                    {
                            {0, -1},
                            {0,  0}, {1,  0},
                            {1,  1}
                    }
            };
    final static int rotationPermutations = 2;

    SPiece() {
        super(pieceCoordsByRotation, rotationPermutations);
    }

}
