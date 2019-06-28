package tetrisrevision.tetrominos;

import tetrisrevision.tetrominos.Tetromino;
import com.newtetris.tetrispiece.kick.Kick;

class JPiece extends Tetromino {
    private final static int[][][] offsets =
            {      {{-1,-1},
                    {-1, 0}, {0,  0}, {1, 0}},

                    {{0, 1}, {1, -1},
                    {0,  0},
                    {0, -1}},

                    {{-1, 0}, {0,  0}, {1, 0},
                                       {1, 1}},

                            {{0, 1},
                            {0,  0},
                    {-1, 1},{0, -1}},
            };

    JPiece() {
        super(offsets, new Kick(), "JPiece");
    }
}
