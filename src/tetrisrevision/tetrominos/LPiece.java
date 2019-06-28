package tetrisrevision.tetrominos;

import com.newtetris.tetrispiece.kick.Kick;

class LPiece extends Tetromino {
    private final static int[][][] offsets =
            {
                                    {{1,-1},
                    {-1, 0}, {0, 0}, {1, 0}},

                    {{0,-1},
                     {0, 0},
                     {0, 1}, {1, 1}},

                    {{-1, 0}, {0, 0}, {1, 0},
                     {-1, 1}},

                    {{-1,-1},{0,-1},
                             {0, 0},
                             {0, 1}}

            };
    private final static int orientations = 4;

    LPiece() {
        super(offsets, new Kick(), "LPiece");
    }
}