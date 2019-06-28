package tetrisrevision.tetrominos;

import com.newtetris.tetrispiece.kick.Kick;

class SPiece extends Tetromino {
    private final static int[][][] offsets =
            {
                            {{0,-1}, {1,-1},
                    {-1, 0}, {0, 0}},

                            {{0,-1},
                             {0, 0}, {1, 0},
                                     {1, 1}},

                            {{0, 0}, {1, 0},
                     {-1, 1},{0, 1}},

                    {{-1,-1},
                     {-1, 0},{0, 0},
                             {0, 1}}
            };

    SPiece() {
        super(offsets, new Kick(), "SPiece");
    }
}


