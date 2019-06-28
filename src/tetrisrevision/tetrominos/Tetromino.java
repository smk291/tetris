package tetrisrevision.tetrominos;

import tetrisrevision.kickdata.KickData;
import tetrisrevision.PlayField;
import tetrisrevision.TetrisPiece;

import java.util.HashMap;

public class Tetromino {
    private int[][][] offsets;
    private KickData kickData;
    private String name;

    Tetromino (int[][][] offsets, KickData kickData, String name) {
        this.offsets = offsets;
        this.kickData = kickData;
        this.name = name;
    }

    private TetrisPiece setNewPiece(PlayField p) {
        return new TetrisPiece(
               p,
               this
        );
    }

    private void resetPiece(TetrisPiece t) {
        t.reset(this);
    }

    public int[][][] getOffsets() {
        return this.offsets;
    }

    public int[][] getOffsetsByOrientation(int orientation) {
        return this.offsets[orientation];
    }

    public HashMap<Integer, HashMap<Integer, Integer[][]>>  getKickData() {
        return kickData.get();
    }

    public void setKickData (HashMap<Integer, HashMap<Integer, Integer[][]>> kickData) {
        this.kickData.setKickData(kickData);
    }
}
