package com.newtetris.pieces;

public class JPiece extends Tetromino {
    private final static int[][][] pieceCoordsByRotation =
            {
                    {
                                     {0, -1},
                                    /* {0,  0}, */
                            {-1, 1}, {0,  1}
                    },
                    {
                            {-1,-1},
                            {-1, 0},/*{0,  0},*/{1,  0}
                    },
                    {
                                     {0, -1}, {1, -1},
                                   /*{0,  0},*/
                                     {0,  1}
                    },
                    {
                            {-1, 0},/*{0,  0},*/{1,  0},
                                              {1,  1}
                    }
            };
    private final static int rotationPermutations = 4;

    JPiece() {
        super(pieceCoordsByRotation, rotationPermutations);
    }

}
