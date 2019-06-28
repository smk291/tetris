package tetrisrevision.tetrominos;

import tetrisrevision.kickdata.KickData;
import tetrisrevision.kickdata.KickIPiece;

class IPiece extends Tetromino {
    private final static int[][][] offsets = {

            {{-1, 0}, {0, 0}, {1, 0}, {2, 0}},

                                 {{1, -1},
                                  {1, 0},
                                  {1, 1},
                                  {1, 2}},

            {{-1, 1}, {0, 1}, {1, 1}, {2, 1}},

                     {{0, -1},
                      {0, 0},
                      {0, 1},
                      {0, 2}}};

    public IPiece(){
        super(offsets, new KickData(), "IPiece");

        super.setKickData(new KickIPiece().get());
    }
}
