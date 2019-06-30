package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class TetrisPiece {
    private int prevRotation;
    private int rotation;
    private Point center;
    private Tetromino tetromino;
    private boolean addToBoard;

    public TetrisPiece(Tetromino t) {
        resetSelf();
        this.tetromino = t;
    }

    void reset(Tetromino t) {
        resetSelf();
        this.tetromino = t;
    }

    private void resetSelf() {
        this.center = new Point(4, 0);
        this.rotation = 0;
        addToBoard = false;
    }

    void setFromTetromino(Tetromino t) {
        this.tetromino = t;
    }

    int getRotation() {
        return this.rotation;
    }

    void setRotation(int o) {
        this.rotation = o;
    }

    void incrOrientation(int i) {
        this.rotation += i;
    }

    int getPrevRotation() {
        return prevRotation;
    }

    void setPrevRotation(int r) {
        this.prevRotation = r;
    }

    Point[] getPieceLocation() {
        return Arrays.stream(tetromino.getOffsets()[rotation]).map(p -> new Point(
                (int) this.center.getX() + p[0],
                (int) this.center.getY() + p[1]
        )).toArray(Point[]::new);
    }

    int getRotationMax() {
        return tetromino.getOffsets().length;
    }

    HashMap<Integer, HashMap<Integer, Integer[][]>> getKickData() {
        return tetromino.getKickData();
    }

    void setCenter(Point pt) {
        this.center = pt;
    }

    void setCenter(int x, int y) {
        this.center = new Point(x, y);
    }

    boolean isAddToBoard() {
        return addToBoard;
    }

    void setAddToBoard(boolean addToBoard) {
        this.addToBoard = addToBoard;
    }

    Point getCenter() {
        return center;
    }
}
