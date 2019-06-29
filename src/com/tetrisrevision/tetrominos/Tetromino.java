package com.tetrisrevision.tetrominos;

import com.tetrisrevision.kickdata.KickData;

import java.util.HashMap;

public class Tetromino {
    private int[][][] offsets;
    private KickData kickData;

    Tetromino (int[][][] offsets, KickData kickData) {
        this.offsets = offsets;
        this.kickData = kickData;
    }

    public int[][][] getOffsets() {
        return this.offsets;
    }

    public HashMap<Integer, HashMap<Integer, Integer[][]>>  getKickData() {
        return kickData.get();
    }

    public void setKickData (HashMap<Integer, HashMap<Integer, Integer[][]>> kickData) {
        this.kickData.setKickData(kickData);
    }
}
