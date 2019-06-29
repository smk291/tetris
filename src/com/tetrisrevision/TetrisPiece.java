package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;

public class TetrisPiece {
    private static PlayField p;
    private int[][][] offsets;
    private int prevOrientation;
    private int orientation;
    private int orientationMax;
    private Point center;
    private HashMap<Integer, HashMap<Integer, Integer[][]>> kickData;
    private boolean addToBoard;

    public TetrisPiece(Tetromino tetromino) {
        resetSelf();
        setFromTetromino(tetromino);
    }

    public static void setStaticVariables(PlayField p) {
        TetrisPiece.p = p;
    }

    public void reset(Tetromino t) {
        resetSelf();
        setFromTetromino(t);
    }

    public void resetSelf() {
        this.center = new Point(4, 0);
        this.orientation = 0;
        addToBoard = false;
    }

    public void setFromTetromino(Tetromino t) {
        this.orientationMax = t.getOffsets().length;
        this.offsets = t.getOffsets();
        this.kickData = t.getKickData();
    }

    public void translateCenter(int x, int y) {
        this.center.setLocation(
                this.center.getX() + x,
                this.center.getY() + y
        );
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int o) {
        this.orientation = o;
    }

    public void incrOrientation(int i) {
        this.orientation += i;
    }

    public int getPrevOrientation() {
        return prevOrientation;
    }

    public void setPrevOrientation(int prevOrientation) {
        this.prevOrientation = prevOrientation;
    }

    public Point[] getPieceLocation() {
        return Arrays.stream(offsets[orientation]).map(p ->
            new Point((int) this.center.getX() + p[0], (int) this.center.getY() + p[1])
        ).toArray(Point[]::new);
    }

    public int getOrientationMax() {
        return orientationMax;
    }

    public HashMap<Integer, HashMap<Integer, Integer[][]>> getKickData() {
        return kickData;
    }

    public void setCenter(Point pt) {
        this.center = pt;
    }

    public void setCenter(int x, int y) {
        this.center = new Point(x, y);
    }

    public boolean isAddToBoard() {
        return addToBoard;
    }

    public void setAddToBoard(boolean addToBoard) {
        this.addToBoard = addToBoard;
    }
}
