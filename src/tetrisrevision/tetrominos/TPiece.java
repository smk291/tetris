package tetrisrevision.tetrominos;

import com.newtetris.tetrispiece.kick.Kick;

class TPiece extends Tetromino {
    private final static int[][][] offsets =
            {
                             {{0, -1},
                    {-1, 0}, {0, 0}, {1, 0}},

                             {{0, -1},
                              {0, 0}, {1, 0},
                              {0, 1}},

                    {{-1, 0}, {0, 0}, {1, 0},
                              {0, 1}},

                            {{0, -1},
                    {-1, 0}, {0, 0},
                             {0, 1}}
            };
    private final static int orientations = 4;

    TPiece() {
        super(offsets, new Kick(), "TPIece");
    }

}

